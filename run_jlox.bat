@echo off

echo Current Directory: %cd%

set JVM=F:\Dev_Tools\jdk\jdk-15.0.2\bin\java.exe

set JVM_FLAGS= ^
--class-path .\build

set LIBRARIES=

call %JVM% %JVM_FLAGS% com.craftinginterpreters.lox.Lox
