$mysqlExe = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
$args = @("-u", "root", "-pjayesh0311", "-e", "USE tms; ALTER TABLE account MODIFY password VARCHAR(255);")
& $mysqlExe $args
