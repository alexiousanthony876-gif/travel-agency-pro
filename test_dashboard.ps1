$projectRoot = $PSScriptRoot
$binDir = Join-Path $projectRoot "bin"
$libDir = Join-Path $projectRoot "lib"

# 1. Compile
Write-Host "Compiling..."
if (Test-Path $binDir) { Remove-Item -Path $binDir -Recurse -Force }
New-Item -ItemType Directory -Path $binDir | Out-Null
& javac -d $binDir -cp "$libDir/*" *.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation Success."
    
    # 2. Copy Icons
    $iconDest = Join-Path $binDir "travel_and_Tourism_Organisation_System/icons"
    if (-not (Test-Path $iconDest)) { New-Item -ItemType Directory -Path $iconDest -Force | Out-Null }
    Copy-Item "icons/*" -Destination $iconDest -Recurse -Force
    
    # 3. Run Dashboard
    Write-Host "Running Dashboard..."
    & java -cp "$binDir;$libDir/*" travel_and_Tourism_Organisation_System.Dashboard
} else {
    Write-Host "Compilation Failed."
}
