@echo off

set BYTECODE_DIRECTORY= ^
.\build

set COMPILER_FLAGS= ^
-g ^
-Xlint

set LIBRARIES= .\build


rem START BUILD
rem ****************************************

call F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe ^
%COMPILER_FLAGS% ^
-d%BYTECODE_DIRECTORY% ^
.\src\java\Token.java ^
.\src\java\TokenType.java ^
.\src\java\Scanner.java ^
.\src\java\Lox.java


