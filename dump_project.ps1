<#
.SYNOPSIS
    Dumps the whole Salati project (Kotlin, resources, Gradle, tests, config) into one Markdown file for review.

.DESCRIPTION
    Run from the project root. Output goes to .\extractions\ (added to .gitignore if missing).
    Included: .kt .kts .java .xml .gradle .toml .properties .json .md .yml .yaml .pro .txt .gitignore .gitattributes
      (so src\main, src\test, src\androidTest, res\, AndroidManifest.xml, Room schemas, CI files are all covered).
    Skipped: build outputs, .git, .gradle, .idea, binaries and images, keystores, local.properties,
      google-services.json, files over -MaxFileSizeKB, and anything containing NUL bytes.
    Likely secrets (password / token / secret / api key values) are replaced with <REDACTED>.
    Still skim the output before sharing it.

.EXAMPLE
    powershell -ExecutionPolicy Bypass -File .\dump_project.ps1

.EXAMPLE
    # Split into parts of at most 2 MB each (Salati_full_part1.md, _part2.md, ...)
    powershell -ExecutionPolicy Bypass -File .\dump_project.ps1 -MaxPartSizeMB 2
#>
[CmdletBinding()]
param(
    [string]$ProjectRoot = (Get-Location).Path,
    [string]$OutputDir,
    [string]$OutputName = 'Salati_full',
    [int]$MaxFileSizeKB = 300,
    [int]$MaxPartSizeMB = 0
)

$ErrorActionPreference = 'Stop'

$root = (Resolve-Path -LiteralPath $ProjectRoot).Path.TrimEnd('\')
if (-not $OutputDir) { $OutputDir = Join-Path $root 'extractions' }
New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null

$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

$excludedDirs = @('.git', '.gradle', '.idea', '.kotlin', 'build', 'extractions', 'node_modules', '.cxx', 'captures', '.externalNativeBuild')
$excludedFileNames = @('local.properties', 'keystore.properties', 'google-services.json')
$includedExtensions = @('.kt', '.kts', '.java', '.xml', '.gradle', '.toml', '.properties', '.json', '.md', '.yml', '.yaml', '.pro', '.txt', '.gitignore', '.gitattributes')
$languages = @{
    '.kt' = 'kotlin'; '.kts' = 'kotlin'; '.java' = 'java'; '.xml' = 'xml'; '.gradle' = 'groovy'
    '.toml' = 'toml'; '.properties' = 'properties'; '.json' = 'json'; '.md' = 'markdown'
    '.yml' = 'yaml'; '.yaml' = 'yaml'
}

# Walks the tree and skips excluded folders without descending into them (fast, avoids build\ and .git\).
function Get-ProjectFiles([string]$directory) {
    foreach ($entry in Get-ChildItem -LiteralPath $directory -Force) {
        if ($entry.PSIsContainer) {
            if ($excludedDirs -notcontains $entry.Name) { Get-ProjectFiles $entry.FullName }
        }
        else {
            $entry
        }
    }
}

# Replaces values that look like credentials before anything is written out.
function Hide-Secrets([string]$text, [string]$extension) {
    if (@('.gradle', '.kts', '.properties', '.json', '.yml', '.yaml') -contains $extension) {
        $text = [regex]::Replace($text, '(?im)^(\s*[\w.\-"'']*(password|passwd|secret|token|api[_-]?key)[\w.\-"'']*\s*[=:]\s*).+$', '${1}<REDACTED>')
    }
    if ($extension -eq '.kt') {
        $text = [regex]::Replace($text, '(?i)((api[_-]?key|secret|token|password)\w*\s*=\s*)"[^"]{8,}"', '${1}"<REDACTED>"')
    }
    return $text
}

Write-Host "Scanning $root ..."

$included = New-Object System.Collections.Generic.List[object]
$skipped = New-Object System.Collections.Generic.List[string]

$files = Get-ProjectFiles $root | Sort-Object { $_.FullName.Substring($root.Length + 1).ToLowerInvariant() }

foreach ($file in $files) {
    $relative = $file.FullName.Substring($root.Length + 1)
    $extension = $file.Extension.ToLowerInvariant()

    if ($excludedFileNames -contains $file.Name) { $skipped.Add("$relative (secrets file)"); continue }
    if ($includedExtensions -notcontains $extension) { continue }
    if ($file.Length -gt ($MaxFileSizeKB * 1KB)) { $skipped.Add("$relative (larger than $MaxFileSizeKB KB)"); continue }

    $text = [System.IO.File]::ReadAllText($file.FullName, [System.Text.Encoding]::UTF8)
    if ($text.Contains([string][char]0)) { $skipped.Add("$relative (binary)"); continue }

    $text = $text -replace "`r`n", "`n"
    $text = Hide-Secrets $text $extension

    $included.Add([pscustomobject]@{ Relative = $relative; Extension = $extension; Text = $text })
}

# ---- Header + file tree -------------------------------------------------------------------
$projectName = Split-Path $root -Leaf
$gitInfo = 'n/a'
try {
    $branch = (& git -C $root rev-parse --abbrev-ref HEAD 2>$null)
    $hash = (& git -C $root rev-parse --short HEAD 2>$null)
    if ($branch) { $gitInfo = "$branch @ $hash" }
}
catch { }

$treeLines = ($included | ForEach-Object { $_.Relative }) -join "`n"
$header = "# $projectName full project dump`n`n" +
    "- Generated: $(Get-Date -Format 'yyyy-MM-dd HH:mm')`n" +
    "- Git: $gitInfo`n" +
    "- Files included: $($included.Count)`n`n" +
    "## Project files`n`n" + '```text' + "`n$treeLines`n" + '```' + "`n`n"

# ---- Write (optionally split into parts) --------------------------------------------------
$outputFiles = New-Object System.Collections.Generic.List[string]

function New-PartWriter([int]$index) {
    if ($MaxPartSizeMB -gt 0) { $name = '{0}_part{1}.md' -f $OutputName, $index } else { $name = "$OutputName.md" }
    $path = Join-Path $OutputDir $name
    $script:outputFiles.Add($path)
    return New-Object System.IO.StreamWriter($path, $false, $utf8NoBom)
}

$limitBytes = [long]$MaxPartSizeMB * 1MB
$partIndex = 1
$writer = New-PartWriter $partIndex
$partBytes = 0L

$writer.Write($header)
$partBytes += $utf8NoBom.GetByteCount($header)

foreach ($item in $included) {
    $language = ''
    if ($languages.ContainsKey($item.Extension)) { $language = $languages[$item.Extension] }
    # Markdown files may contain ``` themselves, so they get a longer fence.
    $fence = '```'
    if ($item.Extension -eq '.md') { $fence = '````' }

    $section = "## $($item.Relative)`n`n" + $fence + $language + "`n" + $item.Text.TrimEnd() + "`n" + $fence + "`n`n"
    $bytes = $utf8NoBom.GetByteCount($section)

    if ($limitBytes -gt 0 -and $partBytes -gt 0 -and ($partBytes + $bytes) -gt $limitBytes) {
        $writer.Dispose()
        $partIndex++
        $writer = New-PartWriter $partIndex
        $partBytes = 0L
    }

    $writer.Write($section)
    $partBytes += $bytes
}

if ($skipped.Count -gt 0) {
    $skippedSection = "## Skipped files`n`n" + (($skipped | ForEach-Object { "- $_" }) -join "`n") + "`n"
    $writer.Write($skippedSection)
}
$writer.Dispose()

# ---- Keep extractions\ out of git ---------------------------------------------------------
$gitignorePath = Join-Path $root '.gitignore'
if (Test-Path -LiteralPath $gitignorePath) {
    $gitignoreText = [System.IO.File]::ReadAllText($gitignorePath)
    if ($gitignoreText -notmatch '(?m)^/?extractions/?\s*$') {
        [System.IO.File]::AppendAllText($gitignorePath, "`nextractions/`n", $utf8NoBom)
        Write-Host 'Added extractions/ to .gitignore'
    }
}

Write-Host ''
Write-Host "Done: $($included.Count) files included, $($skipped.Count) skipped."
foreach ($path in $outputFiles) {
    Write-Host ('  {0}  ({1:N0} KB)' -f $path, ((Get-Item -LiteralPath $path).Length / 1KB))
}
