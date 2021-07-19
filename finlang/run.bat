@echo off

echo [BATCH] Working Directory: %cd%


rem DEFINITIONS
rem ****************************************

set JVM=F:\Dev_Tools\jdk\jdk-15.0.2\bin\java.exe

set JVM_FLAGS= ^
--class-path .\build

set LIBRARIES=


rem RUN
rem ****************************************
if "%1" equ ""       (goto :FINLANG)
if "%1" equ "astgen" (goto :ASTGEN )
if "%1" equ "help"   (goto :HELP   )

:HELP
echo Usage: %~nx0 [command]
echo.
echo By default builds finlang.
echo.
echo Optional [command] can be:
echo   astgen  - runs the abstract synax tree generator

goto :eof

:FINLANG
%JVM% %JVM_FLAGS% com.finlang.lang.Finlang
goto :eof

:ASTGEN
echo running ast generator.
%JVM% %JVM_FLAGS% com.finlang.tools.GenerateAst
goto :eof
