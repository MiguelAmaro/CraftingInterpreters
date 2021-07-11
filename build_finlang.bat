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
.\src\java\AstPrinter.java ^
.\src\java\Environment.java ^
.\src\java\Expr.java ^
.\src\java\Interpreter.java ^
.\src\java\Lox.java ^
.\src\java\LoxCallable.java ^
.\src\java\LoxClass.java ^
.\src\java\LoxFunction.java ^
.\src\java\LoxInstance.java ^
.\src\java\Parser.java ^
.\src\java\Resolver.java ^
.\src\java\Return.java ^
.\src\java\RuntimeError.java ^
.\src\java\Scanner.java ^
.\src\java\Stmt.java ^
.\src\java\Token.java ^
.\src\java\TokenType.java


