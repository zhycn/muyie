::显示依赖版本号变更的脚本

@echo off
echo.
echo [INFO] mvnw versions:display-property-updates
echo.

cd ..
call mvnw versions:display-property-updates

cd sbin
pause
