@echo off

echo building jlox

set JAVAC=F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe

set JVAC_BYTECODE_DIR= .\build

set JVAC_FLAGS= ^
-g ^
-Xlint ^
-d%JVAC_BYTECODE_DIR%

set LIBRARIES=

rem START BUILD
rem ****************************************

call %JAVAC% %JVAC_FLAGS% ^
.\src\AstPrinter.java ^
.\src\Environment.java ^
.\src\Expr.java ^
.\src\Interpreter.java ^
.\src\Lox.java ^
.\src\LoxCallable.java ^
.\src\LoxClass.java ^
.\src\LoxFunction.java ^
.\src\LoxInstance.java ^
.\src\Parser.java ^
.\src\Resolver.java ^
.\src\Return.java ^
.\src\RuntimeError.java ^
.\src\Scanner.java ^
.\src\Stmt.java ^
.\src\Token.java ^
.\src\TokenType.java


