::项目打包的脚本

@echo off
echo.
echo [INFO] mvnw clean package
echo.

cd ..
call mvnw clean package -DcreateChecksum=true -DskipTests -Dgpg.skip -U

cd sbin
pause
