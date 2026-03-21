$ErrorActionPreference = "Stop"

Write-Host "Setting up Database..." -ForegroundColor Cyan

# Function to find executable
function Get-MySQLExecutable {
    if (Get-Command "mysql" -ErrorAction SilentlyContinue) {
        return "mysql"
    }
    
    $searchPaths = @(
        "C:\Program Files\MySQL\MySQL Server*\bin\mysql.exe",
        "C:\Program Files (x86)\MySQL\MySQL Server*\bin\mysql.exe"
    )
    
    foreach ($path in $searchPaths) {
        $found = Get-ChildItem -Path $path -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($found) {
            return $found.FullName
        }
    }
    return $null
}

$mysqlExe = Get-MySQLExecutable

if ($mysqlExe) {
    Write-Host "MySQL found at: $mysqlExe"
    Write-Host "Attempting to create database..."
    
    # Prompt for user password
    # Auto-set user password
    $password = "jayesh0311"
    
    $cmdArgs = @("-u", "root")
    if (-not [string]::IsNullOrWhiteSpace($password)) {
        $cmdArgs += "-p$password"
    }

    $sqlFile = Join-Path $PSScriptRoot "database_setup.sql"
    
    try {
        # Redirect input from file
        # Using specific executable path
        $procInfo = New-Object System.Diagnostics.ProcessStartInfo
        $procInfo.FileName = $mysqlExe
        $procInfo.Arguments = "$($cmdArgs -join ' ') -e ""source $sqlFile"""
        $procInfo.RedirectStandardOutput = $true
        $procInfo.RedirectStandardError = $true
        $procInfo.UseShellExecute = $false
        $procInfo.CreateNoWindow = $true
        
        $process = New-Object System.Diagnostics.Process
        $process.StartInfo = $procInfo
        $process.Start() | Out-Null
        $process.WaitForExit()
        
        $stdout = $process.StandardOutput.ReadToEnd()
        $stderr = $process.StandardError.ReadToEnd()
        
        if ($process.ExitCode -eq 0) {
            Write-Host "Database setup complete!" -ForegroundColor Green
            Write-Host $stdout
        } else {
            Write-Host "Database setup failed." -ForegroundColor Red
            Write-Host "Error Output:"
            Write-Host $stderr
        }
    } catch {
        Write-Host "Error running mysql command: $_" -ForegroundColor Red
    }
} else {
    Write-Host "MySQL command line tool not found." -ForegroundColor Red
    Write-Host "Please install MySQL Server from https://dev.mysql.com/downloads/mysql/"
    Write-Host "Or verify it is installed in the standard 'Program Files' directory."
}

# pause
