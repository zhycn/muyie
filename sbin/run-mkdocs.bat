::本地运行项目文档的脚本

@echo off
echo.
echo [INFO] mkdocs serve
echo.

cd ..
call mkdocs serve

cd sbin
pause
