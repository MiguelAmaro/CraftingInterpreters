@echo off

echo building jlox

set JAVAC=F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe

set JVAC_BYTECODE_DIR= ^
.\build

set JVAC_FLAGS= ^
-g ^
-Xlint ^
-d%JVAC_BYTECODE_DIR%

set LIBRARIES=

rem START BUILD
rem ****************************************

call %JAVAC% %JVAC_FLAGS% %JVAC_BYTECODE_DIR%^
.\src\java\jlox\AstPrinter.java ^
.\src\java\jlox\Environment.java ^
.\src\java\jlox\Expr.java ^
.\src\java\jlox\Interpreter.java ^
.\src\java\jlox\Lox.java ^
.\src\java\jlox\LoxCallable.java ^
.\src\java\jlox\LoxClass.java ^
.\src\java\jlox\LoxFunction.java ^
.\src\java\jlox\LoxInstance.java ^
.\src\java\jlox\Parser.java ^
.\src\java\jlox\Resolver.java ^
.\src\java\jlox\Return.java ^
.\src\java\jlox\RuntimeError.java ^
.\src\java\jlox\Scanner.java ^
.\src\java\jlox\Stmt.java ^
.\src\java\jlox\Token.java ^
.\src\java\jlox\TokenType.java


