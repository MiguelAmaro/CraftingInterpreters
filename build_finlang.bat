@echo off

echo building finlang

set JAVAC=F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe

set JAVAC_BYTECODE_DIR=^
-d .\build

set JAVAC_FLAGS= ^
-g ^
-Xlint

set LIBRARIES= .\build


rem START BUILD
rem ****************************************

call %JAVAC% %JAVAC_FLAGS% %JAVAC_BYTECODE_DIR% ^
.\src\finlang\FLScanner.java ^
.\src\finlang\FLParser.java ^
.\src\finlang\FLAstPrinter.java ^
.\src\finlang\FLInterpreter.java 
