<#
.SYNOPSIS
    Extract @Composable functions from Kotlin source files under a package
    directory and dump them into a single Markdown file for review.

.USAGE
    # Run with default presets (extracts your whole project codebase):
    .\scripts\extract_composables.ps1

    # Run with custom parameters:
    .\scripts\extract_composables.ps1 -SourceRoot app/src/main/java/com/mhq/salati/home -OutputFile extractions/composables_home.md
#>

param(
    [Parameter(Mandatory = $false)]
    [string]$SourceRoot = (Join-Path -Path (Split-Path -Path $PSScriptRoot -Parent) -ChildPath "app\src\main\java"),

    [Parameter(Mandatory = $false)]
    [string]$OutputFile = (Join-Path -Path (Split-Path -Path $PSScriptRoot -Parent) -ChildPath "extractions\all_composables.md")
)

# The script now lives in scripts\, so the project root is its parent folder.
$ProjectRoot = Split-Path -Path $PSScriptRoot -Parent

if (-not (Test-Path $SourceRoot)) {
    Write-Error "Path not found: $SourceRoot"
    exit 1
}

$composableRegex = [regex]'(?ms)((?:@\w+(?:\([^)]*\))?\s*)*)(?:(?:public|private|internal|protected)\s+)?fun\s+(\w+)\s*\('

function Find-MatchingBrace {
    param([string]$Text, [int]$StartIndex)
    $depth = 0
    for ($i = $StartIndex; $i -lt $Text.Length; $i++) {
        $ch = $Text[$i]
        if ($ch -eq '{') { $depth++ }
        elseif ($ch -eq '}') {
            $depth--
            if ($depth -eq 0) { return $i }
        }
    }
    return -1
}

function Get-ComposablesFromFile {
    param([string]$FilePath)

    $text = Get-Content -Raw -Encoding UTF8 -Path $FilePath
    $results = @()

    foreach ($m in $composableRegex.Matches($text)) {
        $annotations = $m.Groups[1].Value
        if ($annotations -notmatch '@Composable') { continue }
        $funcName = $m.Groups[2].Value

        # find end of parameter list (matching parens)
        $parenStart = $m.Index + $m.Length - 1
        $depth = 0
        $i = $parenStart
        while ($i -lt $text.Length) {
            $ch = $text[$i]
            if ($ch -eq '(') { $depth++ }
            elseif ($ch -eq ')') {
                $depth--
                if ($depth -eq 0) { break }
            }
            $i++
        }
        $sigEnd = $i + 1

        $braceStart = $text.IndexOf('{', $sigEnd)
        $eqPos = $text.IndexOf('=', $sigEnd)

        if ($braceStart -eq -1 -or ($eqPos -ne -1 -and $eqPos -lt $braceStart)) {
            # expression-body function: fun Foo() = ...
            $endIdx = $text.IndexOf("`n`n", $sigEnd)
            if ($endIdx -eq -1) { $endIdx = $text.Length }
            $snippet = $text.Substring($m.Index, $endIdx - $m.Index).Trim()
        }
        else {
            $braceEnd = Find-MatchingBrace -Text $text -StartIndex $braceStart
            if ($braceEnd -eq -1) { continue }
            $snippet = $text.Substring($m.Index, $braceEnd - $m.Index + 1).Trim()
        }

        $results += [PSCustomObject]@{ Name = $funcName; Snippet = $snippet }
    }

    return $results
}

$ktFiles = Get-ChildItem -Path $SourceRoot -Filter *.kt -Recurse | Sort-Object FullName
$rootFull = (Resolve-Path $SourceRoot).Path

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("# Composables under $SourceRoot")
$lines.Add("")

$total = 0

foreach ($file in $ktFiles) {
    $composables = Get-ComposablesFromFile -FilePath $file.FullName
    if ($composables.Count -eq 0) { continue }

    $rel = $file.FullName.Substring($rootFull.Length).TrimStart('\', '/')
    $lines.Add("## $rel")
    $lines.Add("")

    foreach ($c in $composables) {
        $total++
        $lines.Add("### $($c.Name)")
        $lines.Add("")
        $lines.Add('```kotlin')
        $lines.Add($c.Snippet)
        $lines.Add('```')
        $lines.Add("")
    }
}

$outDir = Split-Path -Path $OutputFile -Parent
if ($outDir -and -not (Test-Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir -Force | Out-Null
}

$lines | Set-Content -Encoding UTF8 -Path $OutputFile

Write-Host "Extracted $total composable function(s) from $($ktFiles.Count) file(s) into $OutputFile"



# <#
# .SYNOPSIS
#     Extract @Composable functions from Kotlin source files under a package
#     directory and dump them into a single Markdown file for review.
#
# .USAGE
#     .\extract_composables.ps1 -SourceRoot <path> -OutputFile <path>
#
# .EXAMPLE
#     .\extract_composables.ps1 -SourceRoot app/src/main/java/com/mhq/salati/home -OutputFile extractions/composables_home.md
# #>
#
# param(
#     [Parameter(Mandatory = $true)][string]$SourceRoot,
#     [Parameter(Mandatory = $true)][string]$OutputFile
# )
#
# if (-not (Test-Path $SourceRoot)) {
#     Write-Error "Path not found: $SourceRoot"
#     exit 1
# }
#
# $composableRegex = [regex]'(?ms)((?:@\w+(?:\([^)]*\))?\s*)*)(?:(?:public|private|internal|protected)\s+)?fun\s+(\w+)\s*\('
#
# function Find-MatchingBrace {
#     param([string]$Text, [int]$StartIndex)
#     $depth = 0
#     for ($i = $StartIndex; $i -lt $Text.Length; $i++) {
#         $ch = $Text[$i]
#         if ($ch -eq '{') { $depth++ }
#         elseif ($ch -eq '}') {
#             $depth--
#             if ($depth -eq 0) { return $i }
#         }
#     }
#     return -1
# }
#
# function Get-ComposablesFromFile {
#     param([string]$FilePath)
#
#     $text = Get-Content -Raw -Encoding UTF8 -Path $FilePath
#     $results = @()
#
#     foreach ($m in $composableRegex.Matches($text)) {
#         $annotations = $m.Groups[1].Value
#         if ($annotations -notmatch '@Composable') { continue }
#         $funcName = $m.Groups[2].Value
#
#         # find end of parameter list (matching parens)
#         $parenStart = $m.Index + $m.Length - 1
#         $depth = 0
#         $i = $parenStart
#         while ($i -lt $text.Length) {
#             $ch = $text[$i]
#             if ($ch -eq '(') { $depth++ }
#             elseif ($ch -eq ')') {
#                 $depth--
#                 if ($depth -eq 0) { break }
#             }
#             $i++
#         }
#         $sigEnd = $i + 1
#
#         $braceStart = $text.IndexOf('{', $sigEnd)
#         $eqPos = $text.IndexOf('=', $sigEnd)
#
#         if ($braceStart -eq -1 -or ($eqPos -ne -1 -and $eqPos -lt $braceStart)) {
#             # expression-body function: fun Foo() = ...
#             $endIdx = $text.IndexOf("`n`n", $sigEnd)
#             if ($endIdx -eq -1) { $endIdx = $text.Length }
#             $snippet = $text.Substring($m.Index, $endIdx - $m.Index).Trim()
#         }
#         else {
#             $braceEnd = Find-MatchingBrace -Text $text -StartIndex $braceStart
#             if ($braceEnd -eq -1) { continue }
#             $snippet = $text.Substring($m.Index, $braceEnd - $m.Index + 1).Trim()
#         }
#
#         $results += [PSCustomObject]@{ Name = $funcName; Snippet = $snippet }
#     }
#
#     return $results
# }
#
# $ktFiles = Get-ChildItem -Path $SourceRoot -Filter *.kt -Recurse | Sort-Object FullName
# $rootFull = (Resolve-Path $SourceRoot).Path
#
# $lines = New-Object System.Collections.Generic.List[string]
# $lines.Add("# Composables under ``$SourceRoot``")
# $lines.Add("")
#
# $total = 0
#
# foreach ($file in $ktFiles) {
#     $composables = Get-ComposablesFromFile -FilePath $file.FullName
#     if ($composables.Count -eq 0) { continue }
#
#     $rel = $file.FullName.Substring($rootFull.Length).TrimStart('\', '/')
#     $lines.Add("## $rel")
#     $lines.Add("")
#
#     foreach ($c in $composables) {
#         $total++
#         $lines.Add("### $($c.Name)")
#         $lines.Add("")
#         $lines.Add('```kotlin')
#         $lines.Add($c.Snippet)
#         $lines.Add('```')
#         $lines.Add("")
#     }
# }
#
# $outDir = Split-Path -Path $OutputFile -Parent
# if ($outDir -and -not (Test-Path $outDir)) {
#     New-Item -ItemType Directory -Path $outDir -Force | Out-Null
# }
#
# $lines | Set-Content -Encoding UTF8 -Path $OutputFile
#
# Write-Host "Extracted $total composable function(s) from $($ktFiles.Count) file(s) into $OutputFile"
