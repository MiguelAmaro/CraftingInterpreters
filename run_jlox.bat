@echo off

echo Current Directory: %cd%

set JVM_FLAGS= ^
--class-path .\build

set LIBRARIES=

call F:\Dev_Tools\jdk\jdk-15.0.2\bin\java.exe %JVM_FLAGS% com.craftinginterpreters.lox.Lox
