This repo contains my work on learning to write compilers/interpeters on a basic level.
I am using https://craftinginterpreters.com as you may have figured from the repo name.
This is a book I've been wanting to read and I took my Java class's final project as an
opportunity to force myself to read the Java section of this book. Currently the goal is
to write a modified version of jlox. 

This repo contains jlox as seen in the book aswell as finlang my version of it. 

Batch files are used do build and run the project. Just replace the directories after
JAVAC and JVM with the directories of "javc.exe" and "java.exe" on your machine respectively.
```bat
rem in "build_jlox.bat" and "build_finlang.bat"

set JAVAC=F:\Dev_Tools\jdk\jdk-15.0.2\bin\javac.exe


rem in   "run_jlox.bat" and "run_finlang.bat"

set JVM=F:\Dev_Tools\jdk\jdk-15.0.2\bin\java.exe

```