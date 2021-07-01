@echo off

echo %cd%

set LIBRARIES=

call F:\Dev_Tools\jdk\jdk-15.0.2\bin\java.exe -cp%LIBRARIES% .\build Lox
