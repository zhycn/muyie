::部署安装的脚本（中央仓库）

@echo off
echo.
echo [INFO] mvnw clean deploy
echo.

cd ..
call mvnw clean deploy

cd sbin
pause
