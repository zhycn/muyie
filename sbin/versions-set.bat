::设置项目版本号的脚本

@echo off
echo.
echo [INFO] mvnw versions:set
echo.

cd ..
call mvnw versions:set          -DnewVersion=1.2.13-SNAPSHOT -DgenerateBackupPoms=false
call mvnw versions:set-property -DnewVersion=1.2.13-SNAPSHOT -DgenerateBackupPoms=false -Dproperty=muyie-framework.version

cd sbin
pause
