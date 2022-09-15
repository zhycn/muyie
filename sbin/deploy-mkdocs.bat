::部署项目文档的脚本（Github Pages）

@echo off
echo.
echo [INFO] mkdocs gh-deploy --force
echo.

cd ..
call mkdocs gh-deploy --force
call rmdir /Q /S site

cd sbin
pause
