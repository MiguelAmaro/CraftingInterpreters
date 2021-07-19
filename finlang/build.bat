@echo off


rem DEFINITIONS
rem ****************************************

set JAVAC=F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe

set JAVAC_BYTECODE_DIR= .\build

set JAVAC_FLAGS= ^
-g ^
-Xlint ^
-d%JAVAC_BYTECODE_DIR%

set LIBRARIES= .\build


rem START BUILD
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
echo   astgen    - builds the abstract synax tree generator

goto :eof

:FINLANG
echo building finlang...

%JAVAC% %JAVAC_FLAGS% ^
.\src\Finlang.java ^
.\src\FLExpr.java ^
.\src\FLInterpreter.java ^
.\src\FLParser.java ^
.\src\FLRuntimeError.java ^
.\src\FLScanner.java ^
.\src\FLToken.java ^
.\src\FLTokenType.java
goto :eof

:ASTGEN
echo generating finlang ast...

%JAVAC% %JAVAC_FLAGS% ^
.\src\GenerateAst.java 
goto :eof

rem .\src\FLAstPrinter.java


