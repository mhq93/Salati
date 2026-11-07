<#
.SYNOPSIS
    Exports an Android project (code, resources, gradle, manifest, git state) into markdown file(s)
    that can be uploaded in a chat.

.DESCRIPTION
    - Text files are written as "## relative\path" followed by a fenced code block.
    - Build output (build, .gradle, .idea, .git, ...) is skipped.
    - Binary resources (png, webp, fonts, audio, ...) are listed with their size, not embedded.
    - Secrets are never written: local.properties, google-services.json, keystores and .env files
      are skipped, and string literals assigned to key/secret/password/token names are redacted.
    - The output is split into parts of about -MaxPartKB so each file stays easy to upload.

.PARAMETER ProjectRoot
    The folder that contains settings.gradle(.kts). Defaults to the current folder.

.PARAMETER OutputFile
    Base name of the output. Default Salati_Full.md (parts become Salati_Full_part1.md, ...).

.PARAMETER MaxFileKB
    Text files bigger than this are listed but not embedded. Default 300.

.PARAMETER MaxPartKB
    Target size of each output part. Default 900.

.PARAMETER AppModule
    Gradle module used by -IncludeDependencyTree. Default app.

.PARAMETER ExcludeTests
    Leave out src/test and src/androidTest.

.PARAMETER NoGit
    Do not add git information.

.PARAMETER IncludeDependencyTree
    Also run "gradlew :app:dependencies" (debugRuntimeClasspath) and add the first 700 lines.
    Needs Gradle to be able to run (offline cache or network); can take a few minutes.

.EXAMPLE
    .\Export-SalatiProject.ps1

.EXAMPLE
    .\Export-SalatiProject.ps1 -ProjectRoot D:\Projects\Salati -ExcludeTests -IncludeDependencyTree
#>
[CmdletBinding()]
param(
    [string]$ProjectRoot = (Get-Location).Path,
    [string]$OutputFile = 'Salati_Full.md',
    [int]$MaxFileKB = 300,
    [int]$MaxPartKB = 900,
    [string]$AppModule = 'app',
    [switch]$ExcludeTests,
    [switch]$NoGit,
    [switch]$IncludeDependencyTree
)

$ErrorActionPreference = 'Stop'
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

# ----------------------------------------------------------------------------- setup

$root = (Resolve-Path -LiteralPath $ProjectRoot).Path.TrimEnd('\', '/')
$hasSettings = (Test-Path -LiteralPath (Join-Path $root 'settings.gradle.kts')) -or
               (Test-Path -LiteralPath (Join-Path $root 'settings.gradle'))
if (-not $hasSettings) {
    throw "No settings.gradle(.kts) found in '$root'. Run the script from the project root or pass -ProjectRoot."
}
$rootName = Split-Path $root -Leaf

if ([System.IO.Path]::IsPathRooted($OutputFile)) { $outPath = $OutputFile } else { $outPath = Join-Path $root $OutputFile }
$outDir = Split-Path $outPath -Parent
$outBase = [System.IO.Path]::GetFileNameWithoutExtension($outPath)
$outExt = [System.IO.Path]::GetExtension($outPath)
$scriptName = $MyInvocation.MyCommand.Name
$selfPattern = '^' + [regex]::Escape($outBase) + '(_part\d+)?' + [regex]::Escape($outExt) + '$'

$excludedDirs = @(
    '.git', '.gradle', '.idea', 'build', '.cxx', '.kotlin', 'node_modules', 'captures',
    'out', '.externalNativeBuild', '.fleet'
)

$sensitiveNames = '(?i)^(local\.properties|google-services\.json|keystore\.properties|secrets?\.properties|\.env(\..*)?|service-account.*\.json|.*\.(jks|keystore|p12|pfx|pem|key))$'

$textExtensions = @(
    '.kt', '.kts', '.java', '.xml', '.gradle', '.toml', '.properties', '.pro', '.md', '.json',
    '.txt', '.yml', '.yaml', '.cfg', '.conf', '.html', '.css', '.aidl', '.sh'
)
$textNames = @('.gitignore', '.gitattributes', '.editorconfig', 'proguard-rules.pro')

$languages = @{
    '.kt' = 'kotlin'; '.kts' = 'kotlin'; '.java' = 'java'; '.xml' = 'xml'; '.gradle' = 'groovy'
    '.toml' = 'toml'; '.properties' = 'properties'; '.md' = 'markdown'; '.json' = 'json'
    '.yml' = 'yaml'; '.yaml' = 'yaml'; '.html' = 'html'; '.css' = 'css'; '.sh' = 'bash'
}

# ----------------------------------------------------------------------------- helpers

function Get-ProjectFiles([string]$Directory) {
    foreach ($item in Get-ChildItem -LiteralPath $Directory -Force) {
        if ($item.Attributes -band [System.IO.FileAttributes]::ReparsePoint) { continue }
        if ($item.PSIsContainer) {
            if ($excludedDirs -notcontains $item.Name) { Get-ProjectFiles $item.FullName }
        } else {
            $item
        }
    }
}

function Get-Rank([string]$RelSlash) {
    $name = $RelSlash.Substring($RelSlash.LastIndexOf('/') + 1)
    if ($RelSlash -match '(^|/)src/(test|androidTest)/') { return 5 }
    if ($RelSlash -match '^(settings\.gradle(\.kts)?|build\.gradle(\.kts)?|gradle\.properties)$') { return 0 }
    if ($RelSlash -match '^gradle/') { return 0 }
    if ($name -match '^(build\.gradle(\.kts)?|proguard-rules\.pro|AndroidManifest\.xml)$') { return 1 }
    if ($RelSlash -match '(^|/)src/main/(java|kotlin)/') { return 2 }
    if ($RelSlash -match '(^|/)src/main/res/') { return 3 }
    if ($RelSlash -match '(^|/)src/main/') { return 4 }
    return 6
}

$script:redactions = 0
function Hide-Secrets([string]$Text) {
    $pattern1 = '(?i)(\b[\w.]*(?:api[_-]?key|secret|password|passwd|token|storepassword|keypassword)[\w.]*\s*[=:]\s*)(["''])[^"''\r\n]{6,}\2'
    $pattern2 = '(?i)(name="[^"]*(?:api[_-]?key|secret|token|password)[^"]*"[^>]*>)[^<]{6,}(</string>)'
    $count = 0
    $evaluator1 = { param($m) $script:redactions++; $m.Groups[1].Value + $m.Groups[2].Value + '<REDACTED>' + $m.Groups[2].Value }
    $evaluator2 = { param($m) $script:redactions++; $m.Groups[1].Value + '<REDACTED>' + $m.Groups[2].Value }
    $Text = [regex]::Replace($Text, $pattern1, $evaluator1)
    $Text = [regex]::Replace($Text, $pattern2, $evaluator2)
    return $Text
}

function Invoke-Git([string[]]$GitArgs) {
    try { return (& git -C $root @GitArgs 2>$null) } catch { return $null }
}

function Format-Size([long]$Bytes) {
    if ($Bytes -ge 1MB) { return ('{0:N1} MB' -f ($Bytes / 1MB)) }
    return ('{0:N1} KB' -f ($Bytes / 1KB))
}

# ----------------------------------------------------------------------------- scan

Write-Host "Scanning $rootName ..." -ForegroundColor Cyan
$allFiles = @(Get-ProjectFiles $root)

$records = New-Object System.Collections.Generic.List[object]
foreach ($f in $allFiles) {
    if ($f.Name -match $selfPattern -and $f.DirectoryName -eq $outDir) { continue }
    if ($f.Name -eq $scriptName) { continue }

    $rel = $f.FullName.Substring($root.Length).TrimStart('\', '/')
    $relSlash = $rel -replace '\\', '/'

    if ($ExcludeTests -and $relSlash -match '(^|/)src/(test|androidTest)/') { continue }

    $ext = $f.Extension.ToLowerInvariant()
    $isText = ($textExtensions -contains $ext) -or ($textNames -contains $f.Name)

    $kind = 'binary'
    if ($f.Name -match $sensitiveNames) {
        $kind = 'sensitive'
    } elseif ($isText) {
        if ($f.Length -gt ($MaxFileKB * 1KB)) { $kind = 'toolarge' } else { $kind = 'text' }
    }

    $records.Add([pscustomobject]@{
        Rel      = ($rel -replace '/', '\')
        RelSlash = $relSlash
        Path     = $f.FullName
        Size     = $f.Length
        Ext      = $ext
        Kind     = $kind
        Rank     = (Get-Rank $relSlash)
    })
}

$sorted = @($records | Sort-Object @{ Expression = 'Rank' }, @{ Expression = 'RelSlash' })
$textRecords = @($sorted | Where-Object { $_.Kind -eq 'text' })

# ----------------------------------------------------------------------------- entries

Write-Host "Reading $($textRecords.Count) text files ..." -ForegroundColor Cyan
$entries = New-Object System.Collections.Generic.List[string]
foreach ($r in $textRecords) {
    try {
        $content = [System.IO.File]::ReadAllText($r.Path, [System.Text.Encoding]::UTF8)
    } catch {
        Write-Warning "Could not read $($r.Rel): $($_.Exception.Message)"
        continue
    }
    $content = $content -replace "`r`n", "`n" -replace "`r", "`n"
    $content = Hide-Secrets $content
    $content = $content.TrimEnd("`n")

    $lang = ''
    if ($languages.ContainsKey($r.Ext)) { $lang = $languages[$r.Ext] }

    $longest = 0
    foreach ($m in [regex]::Matches($content, '`{3,}')) {
        if ($m.Length -gt $longest) { $longest = $m.Length }
    }
    $fence = '`' * [Math]::Max(3, $longest + 1)

    $entries.Add("## $($r.Rel)`n`n$fence$lang`n$content`n$fence`n`n")
}

# ----------------------------------------------------------------------------- header

Write-Host 'Building overview ...' -ForegroundColor Cyan
$h = New-Object System.Text.StringBuilder
[void]$h.AppendLine("# $rootName project export")
[void]$h.AppendLine()
[void]$h.AppendLine("- Generated: $(Get-Date -Format 'yyyy-MM-dd HH:mm')")
[void]$h.AppendLine("- PowerShell: $($PSVersionTable.PSVersion)")
[void]$h.AppendLine("- Files embedded: $($textRecords.Count)")
[void]$h.AppendLine("- Files listed only (binary / too large / sensitive): $($sorted.Count - $textRecords.Count)")
[void]$h.AppendLine('- Secrets: local.properties, google-services.json, keystores and .env files are skipped; key/secret/password/token string literals are replaced with <REDACTED>.')
[void]$h.AppendLine()

if (-not $NoGit -and (Get-Command git -ErrorAction SilentlyContinue) -and (Test-Path -LiteralPath (Join-Path $root '.git'))) {
    [void]$h.AppendLine('## Git')
    [void]$h.AppendLine()
    [void]$h.AppendLine('```text')
    $branch = Invoke-Git @('rev-parse', '--abbrev-ref', 'HEAD')
    [void]$h.AppendLine("branch: $branch")
    $last = Invoke-Git @('log', '-1', '--format=%h %ad %s', '--date=short')
    [void]$h.AppendLine("last commit: $last")
    [void]$h.AppendLine()
    [void]$h.AppendLine('recent commits:')
    foreach ($line in @(Invoke-Git @('log', '-15', '--format=%h %ad %s', '--date=short'))) { [void]$h.AppendLine("  $line") }
    [void]$h.AppendLine()
    [void]$h.AppendLine('working tree changes (git status --short, first 80):')
    $status = @(Invoke-Git @('status', '--short') | Select-Object -First 80)
    if ($status.Count -eq 0) { [void]$h.AppendLine('  (clean)') }
    foreach ($line in $status) { [void]$h.AppendLine("  $line") }
    [void]$h.AppendLine('```')
    [void]$h.AppendLine()
}

if ($IncludeDependencyTree) {
    $gradlew = Join-Path $root 'gradlew.bat'
    if (Test-Path -LiteralPath $gradlew) {
        Write-Host 'Running gradle dependencies (this can take a while) ...' -ForegroundColor Cyan
        $task = ":${AppModule}:dependencies"
        Push-Location $root
        try {
            $deps = @(& $gradlew $task '--configuration' 'debugRuntimeClasspath' '--console=plain' 2>&1 |
                    ForEach-Object { "$_" } | Select-Object -First 700)
        } catch {
            $deps = @("gradle failed: $($_.Exception.Message)")
        } finally {
            Pop-Location
        }
        [void]$h.AppendLine("## Gradle dependencies ($AppModule, debugRuntimeClasspath, first 700 lines)")
        [void]$h.AppendLine()
        [void]$h.AppendLine('```text')
        foreach ($line in $deps) { [void]$h.AppendLine($line) }
        [void]$h.AppendLine('```')
        [void]$h.AppendLine()
    } else {
        Write-Warning 'gradlew.bat not found, skipping the dependency tree.'
    }
}

[void]$h.AppendLine('## Project tree')
[void]$h.AppendLine()
[void]$h.AppendLine('```text')
foreach ($r in $sorted) {
    $tag = ''
    switch ($r.Kind) {
        'binary'    { $tag = '  [binary]' }
        'sensitive' { $tag = '  [skipped: sensitive]' }
        'toolarge'  { $tag = "  [skipped: over $MaxFileKB KB]" }
    }
    [void]$h.AppendLine(('{0}  ({1}){2}' -f $r.Rel, (Format-Size $r.Size), $tag))
}
[void]$h.AppendLine('```')
[void]$h.AppendLine()

$binaryRecords = @($sorted | Where-Object { $_.Kind -eq 'binary' })
if ($binaryRecords.Count -gt 0) {
    [void]$h.AppendLine('## Binary resources by type')
    [void]$h.AppendLine()
    [void]$h.AppendLine('| Type | Files | Total size |')
    [void]$h.AppendLine('|---|---|---|')
    foreach ($g in ($binaryRecords | Group-Object { if ($_.Ext) { $_.Ext } else { '(none)' } } | Sort-Object Count -Descending)) {
        $total = ($g.Group | Measure-Object Size -Sum).Sum
        [void]$h.AppendLine("| $($g.Name) | $($g.Count) | $(Format-Size $total) |")
    }
    [void]$h.AppendLine()
}

[void]$h.AppendLine('---')
[void]$h.AppendLine()
$header = $h.ToString()

# ----------------------------------------------------------------------------- split into parts

$limit = $MaxPartKB * 1KB
$parts = New-Object System.Collections.Generic.List[object]
$current = New-Object System.Collections.Generic.List[string]
$current.Add($header)
$currentBytes = $utf8NoBom.GetByteCount($header)
$entriesInCurrent = 0

foreach ($entry in $entries) {
    $bytes = $utf8NoBom.GetByteCount($entry)
    if ($entriesInCurrent -gt 0 -and ($currentBytes + $bytes) -gt $limit) {
        $parts.Add($current)
        $current = New-Object System.Collections.Generic.List[string]
        $currentBytes = 0
        $entriesInCurrent = 0
    }
    $current.Add($entry)
    $currentBytes += $bytes
    $entriesInCurrent++
}
$parts.Add($current)

# ----------------------------------------------------------------------------- write

if (-not (Test-Path -LiteralPath $outDir)) { New-Item -ItemType Directory -Path $outDir | Out-Null }
Get-ChildItem -LiteralPath $outDir -Filter "${outBase}_part*${outExt}" -File -ErrorAction SilentlyContinue |
    Remove-Item -Force
if (Test-Path -LiteralPath $outPath) { Remove-Item -LiteralPath $outPath -Force }

$written = New-Object System.Collections.Generic.List[string]
for ($i = 0; $i -lt $parts.Count; $i++) {
    if ($parts.Count -eq 1) {
        $target = $outPath
        $prefix = ''
    } else {
        $target = Join-Path $outDir ("{0}_part{1}{2}" -f $outBase, ($i + 1), $outExt)
        $prefix = "<!-- Part $($i + 1) of $($parts.Count) -->`n`n"
    }
    [System.IO.File]::WriteAllText($target, $prefix + ($parts[$i] -join ''), $utf8NoBom)
    $written.Add($target)
}

# ----------------------------------------------------------------------------- summary

Write-Host ''
Write-Host 'Done.' -ForegroundColor Green
foreach ($w in $written) {
    Write-Host ("  {0}  ({1})" -f $w, (Format-Size (Get-Item -LiteralPath $w).Length))
}
Write-Host ("Embedded: {0} files   Listed only: {1}   Redacted values: {2}" -f $textRecords.Count, ($sorted.Count - $textRecords.Count), $script:redactions)

$tooLarge = @($sorted | Where-Object { $_.Kind -eq 'toolarge' })
if ($tooLarge.Count -gt 0) {
    Write-Host "Not embedded (over $MaxFileKB KB), raise -MaxFileKB if you need them:" -ForegroundColor Yellow
    foreach ($t in $tooLarge) { Write-Host "  $($t.Rel)  ($(Format-Size $t.Size))" }
}
$sensitive = @($sorted | Where-Object { $_.Kind -eq 'sensitive' })
if ($sensitive.Count -gt 0) {
    Write-Host 'Skipped as sensitive:' -ForegroundColor Yellow
    foreach ($s in $sensitive) { Write-Host "  $($s.Rel)" }
}
Write-Host 'Upload all generated file(s) to the chat.'