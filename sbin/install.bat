::本地安装的脚本（用于测试）

@echo off
echo.
echo [INFO] mvnw clean install
echo.

cd ..
call mvnw clean install -DcreateChecksum=true -DskipTests -Dgpg.skip -U

cd sbin
pause
