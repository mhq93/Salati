<#
.SYNOPSIS
    Extract the full contents of every .kt file under a package directory
    into a single Markdown file (unlike extract_composables.ps1, this
    captures everything — top-level vals, objects, non-composable code).

.USAGE
    .\scripts\extract_kotlin_files.ps1 -SourceRoot app/src/main/java/com/mhq/salati/shared -OutputFile extractions/shared_infrastructure.md
#>

param(
    [Parameter(Mandatory = $false)]
    [string]$SourceRoot = (Join-Path -Path (Split-Path -Path $PSScriptRoot -Parent) -ChildPath "app\src\main\java"),

    [Parameter(Mandatory = $false)]
    [string]$OutputFile = (Join-Path -Path (Split-Path -Path $PSScriptRoot -Parent) -ChildPath "extractions\all_kotlin_files.md")
)

if (-not (Test-Path $SourceRoot)) {
    Write-Error "Path not found: $SourceRoot"
    exit 1
}

$ktFiles = Get-ChildItem -Path $SourceRoot -Filter *.kt -Recurse | Sort-Object FullName
$rootFull = (Resolve-Path $SourceRoot).Path

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("# Files under $SourceRoot")
$lines.Add("")

foreach ($file in $ktFiles) {
    $rel = $file.FullName.Substring($rootFull.Length).TrimStart('\', '/')
    $content = Get-Content -Raw -Encoding UTF8 -Path $file.FullName

    $lines.Add("## $rel")
    $lines.Add("")
    $lines.Add('```kotlin')
    $lines.Add($content.TrimEnd())
    $lines.Add('```')
    $lines.Add("")
}

$outDir = Split-Path -Path $OutputFile -Parent
if ($outDir -and -not (Test-Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir -Force | Out-Null
}

$lines | Set-Content -Encoding UTF8 -Path $OutputFile

Write-Host "Extracted $($ktFiles.Count) file(s) into $OutputFile"


# <#
# .SYNOPSIS
#     Extract the full contents of every .kt file under a package directory
#     into a single Markdown file (unlike extract_composables.ps1, this
#     captures everything — top-level vals, objects, non-composable code).
#
# .USAGE
#     .\extract_kotlin_files.ps1 -SourceRoot <path> -OutputFile <path>
#
# .EXAMPLE
#     .\extract_kotlin_files.ps1 -SourceRoot app/src/main/java/com/mhq/salati/shared/presentation/theme -OutputFile extractions/theme_files.md
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
# $ktFiles = Get-ChildItem -Path $SourceRoot -Filter *.kt -Recurse | Sort-Object FullName
# $rootFull = (Resolve-Path $SourceRoot).Path
#
# $lines = New-Object System.Collections.Generic.List[string]
# $lines.Add("# Files under ``$SourceRoot``")
# $lines.Add("")
#
# foreach ($file in $ktFiles) {
#     $rel = $file.FullName.Substring($rootFull.Length).TrimStart('\', '/')
#     $content = Get-Content -Raw -Encoding UTF8 -Path $file.FullName
#
#     $lines.Add("## $rel")
#     $lines.Add("")
#     $lines.Add('```kotlin')
#     $lines.Add($content.TrimEnd())
#     $lines.Add('```')
#     $lines.Add("")
# }
#
# $outDir = Split-Path -Path $OutputFile -Parent
# if ($outDir -and -not (Test-Path $outDir)) {
#     New-Item -ItemType Directory -Path $outDir -Force | Out-Null
# }
#
# $lines | Set-Content -Encoding UTF8 -Path $OutputFile
#
# Write-Host "Extracted $($ktFiles.Count) file(s) into $OutputFile"
