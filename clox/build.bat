@echo off
if not exist build mkdir build


rem REFERENCES:https://docs.microsoft.com/en-us/cpp/build/reference/compiler-options-listed-alphabetically?view=vs-2019

rem PROJECT/FILES     
rem ============================================================
set PROJECT_DIR=%cd%
set PROJECT_NAME=main
set SOURCES= %PROJECT_DIR%\src\main.c

rem COMPILER(MSVC)  
rem ============================================================
set MSVC_COMMON=-nologo
set MSVC_WARNINGS=-wd4996 -wd4100 -wd4057

rem NOTE(MIGUEL):-MD is using Dynamic CRT Lib which is what is supporting the console
rem TODE(MIGUEL): Figure out a way to get a console that doesnt need to use the MD flag

set MSVC_FLAGS= -MD -GR -Od -Oi -WX -W4 -FC -Z7 %MSVC_WARNINGS% %MSVC_COMMON%

set INCLUDE_PATHS=


rem LINKER(MSVC)   
rem ============================================================
set LINKER_FLAGS=^
-incremental:no ^
-opt:ref

set LIBRARIES= User32.lib Gdi32.lib Dinput8.lib Dxguid.lib Opengl32.lib Kernel32.lib ^
Ws2_32.lib Shell32.lib

rem START BUILD
rem ============================================================
set path=%PROJECT_DIR%\build;%path%

pushd build
cl %MSVC_FLAGS% %INCLUDE_PATHS% %SOURCES% /link %LIBRARIES%
popd

