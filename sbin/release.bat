::项目发布的脚本

@echo off
echo.
echo [INFO] mvnw release:prepare-with-pom
echo.

cd ..
call mvnw release:prepare-with-pom

cd sbin
pause
