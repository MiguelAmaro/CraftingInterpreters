@echo off

echo building finlang

set JAVAC=F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe

set JAVAC_BYTECODE_DIR= .\build

set JAVAC_FLAGS= ^
-g ^
-Xlint ^
-d%JAVAC_BYTECODE_DIR%

set LIBRARIES= .\build


rem START BUILD
rem ****************************************

call %JAVAC% %JAVAC_FLAGS% ^
.\src\FLScanner.java ^
.\src\FLParser.java ^
.\src\FLAstPrinter.java ^
.\src\FLInterpreter.java 
