$projectRoot = $PSScriptRoot
$binDir = Join-Path $projectRoot "bin"
$libDir = Join-Path $projectRoot "lib"

Write-Host "Compiling..."
if (Test-Path $binDir) { Remove-Item -Path $binDir -Recurse -Force }
New-Item -ItemType Directory -Path $binDir | Out-Null
& javac -d $binDir -cp "$libDir/*" *.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation Success."
    
    # Copy Icons
    $iconDest = Join-Path $binDir "travel_and_Tourism_Organisation_System/icons"
    if (Test-Path $iconDest) { Remove-Item $iconDest -Force -Recurse }
    New-Item -ItemType Directory -Path $iconDest -Force | Out-Null
    Copy-Item "icons/*" -Destination $iconDest -Recurse -Force
    
    Write-Host "Running Application (Login)..."
    & java -cp "$binDir;$libDir/*" travel_and_Tourism_Organisation_System.Login
} else {
    Write-Host "Compilation Failed."
}
