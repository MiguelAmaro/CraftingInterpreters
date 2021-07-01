@echo off

echo %cd%

set Libraries=

call F:\Dev_Tools\jdk\jdk-15.0.2\bin\java.exe -cp%Libraries% .\build Lox
