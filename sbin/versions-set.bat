::设置项目版本号的脚本

@echo off
echo.
echo [INFO] mvnw versions:set
echo.

cd ..
call mvnw versions:set          -DnewVersion=2.7.13-SNAPSHOT -DgenerateBackupPoms=false
call mvnw versions:set-property -DnewVersion=2.7.13-SNAPSHOT -DgenerateBackupPoms=false -Dproperty=muyie-framework.version

cd sbin
pause
