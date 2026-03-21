# Configuration
$projectRoot = $PSScriptRoot
$libDir = Join-Path $projectRoot "lib"
$sqliteJarName = "sqlite-jdbc-3.46.0.0.jar"
$sqliteJarPath = Join-Path $libDir $sqliteJarName
$sqliteJarUrl = "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.46.0.0/sqlite-jdbc-3.46.0.0.jar"

$mysqlJarName = "mysql-connector-j-8.0.33.jar"
$mysqlJarPath = Join-Path $libDir $mysqlJarName

$slf4jApiName = "slf4j-api-1.7.36.jar"
$slf4jApiPath = Join-Path $libDir $slf4jApiName
$slf4jApiUrl = "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar"





$buildDir = $projectRoot # Compiling in-place as per original project structure

Write-Host "=== Travel Agency Project Setup & Run ===" -ForegroundColor Cyan

# 1. Check for Java
Write-Host "1. Checking Java Environment..."
if (-not (Get-Command "javac" -ErrorAction SilentlyContinue)) {
    Write-Host "Error: 'javac' (Java Compiler) not found in PATH." -ForegroundColor Red
    Write-Host "Please install JDK (Java Development Kit) to compile the code."
    Write-Host "You can download it here: https://www.oracle.com/java/technologies/downloads/"
    
    # Try to find JAVA_HOME as fallback
    if ($env:JAVA_HOME) {
        Write-Host "Found JAVA_HOME: $env:JAVA_HOME"
        $javacPath = Join-Path $env:JAVA_HOME "bin\javac.exe"
        if (Test-Path $javacPath) {
            Write-Host "Found javac at JAVA_HOME. Using it..."
            $javacExe = $javacPath
            $javaExe = Join-Path $env:JAVA_HOME "bin\java.exe"
        } else {
             pause
             exit 1
        }
    } else {
        pause
        exit 1
    }
} else {
    $javacExe = "javac"
    $javaExe = "java"
}

# 2. Download Dependencies
Write-Host "2. Checking Dependencies..."
if (-not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir | Out-Null
}

if (-not (Test-Path $sqliteJarPath)) {
    Write-Host "Downloading SQLite Connector..."
    try {
        Invoke-WebRequest -Uri $sqliteJarUrl -OutFile $sqliteJarPath
        Write-Host "Download Complete." -ForegroundColor Green
    } catch {
        Write-Host "Failed to download SQLite Connector. Please check internet connection." -ForegroundColor Red
        pause
        exit 1
    }
} else {
    Write-Host "SQLite Connector found."
}

if (-not (Test-Path $slf4jApiPath)) {
    Write-Host "Downloading SLF4J API..."
    try {
        Invoke-WebRequest -Uri $slf4jApiUrl -OutFile $slf4jApiPath
        Write-Host "Download Complete." -ForegroundColor Green
    } catch {
        Write-Host "Failed to download SLF4J. Please check internet connection." -ForegroundColor Red
        pause
        exit 1
    }
} else {
    Write-Host "SLF4J found."
}





# 3. Compile
Write-Host "3. Compiling Source Code..."
$sources = Get-ChildItem -Path $projectRoot -Filter "*.java" -Recurse

if ($sources) {
    try {
        $binDir = Join-Path $projectRoot "bin"
        if (Test-Path $binDir) { Remove-Item -Path $binDir -Recurse -Force }
        New-Item -ItemType Directory -Path $binDir | Out-Null
        
        $jsonJarPath = Join-Path $libDir "json-20231013.jar"
        $activationJarPath = Join-Path $libDir "activation.jar"
        $javaxMailJarPath = Join-Path $libDir "javax.mail.jar"
        $flatlafJarPath = Join-Path $libDir "flatlaf-3.5.2.jar"
        $classpath = ".;$mysqlJarPath;$sqliteJarPath;$slf4jApiPath;$jsonJarPath;$activationJarPath;$javaxMailJarPath;$flatlafJarPath"
        
        # Compile all files at once to resolve dependencies
        # Using -d to automatically generate package structure in bin
        Write-Host "Compiling $($sources.Count) files..."
        & $javacExe -d "$binDir" --release 8 -encoding UTF-8 -cp $classpath $sources.FullName
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Compilation Successful." -ForegroundColor Green
            
            # Copy icons and other resources to the package directory in bin
            # The package is travel_and_Tourism_Organisation_System
            $packageDir = Join-Path $binDir "travel_and_Tourism_Organisation_System"
            $iconSrc = Join-Path $projectRoot "icons"
            $iconDest = Join-Path $packageDir "icons"
            
            if (Test-Path $iconSrc) {
                Write-Host "Copying icons..."
                if (-not (Test-Path $iconDest)) { New-Item -ItemType Directory -Path $iconDest -Force | Out-Null }
                Copy-Item "$iconSrc\*" -Destination $iconDest -Recurse -Force
            }
            
        } else {
            Write-Host "Compilation Failed." -ForegroundColor Red
            pause
            exit 1
        }
    } catch {
        Write-Host "Error during compilation: $_" -ForegroundColor Red
        pause
        exit 1
    }
} else {
    Write-Host "No Java files found!" -ForegroundColor Red
    pause
    exit 1
}

# 4. Run
Write-Host "4. Running Application..."
Write-Host "Note: If database is missing, it will run in Offline Mode." -ForegroundColor Yellow
try {
    $runClasspath = "$binDir;$mysqlJarPath;$sqliteJarPath;$slf4jApiPath;$jsonJarPath;$activationJarPath;$javaxMailJarPath;$flatlafJarPath"
    & $javaExe -cp $runClasspath travel_and_Tourism_Organisation_System.Login
} catch {
    Write-Host "Error running application: $_" -ForegroundColor Red
    pause
}
