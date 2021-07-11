@echo off
if not exist build mkdir build


rem REFERENCES:https://docs.microsoft.com/en-us/cpp/build/reference/compiler-options-listed-alphabetically?view=vs-2019

rem PROJECT/FILES     
rem ============================================================
set PROJECT_NAME=mylang
set SOURCES= ..\src\Win32_%PROJECT_NAME%.c


rem COMPILER(MSVC)  
rem ============================================================
set MSVC_COMMON=^
-nologo

rem TODO(MIGUEL): ENABLE WARNINGS ONE BY ONE AND RESOLVE
set MSVC_WARNINGS=^
-wd4996

rem NOTE(MIGUEL):-MD is using Dynamic CRT Lib which is what is supporting the console
rem TODE(MIGUEL): Figure out a way to get a console that doesnt need to use the MD flag

set MSVC_FLAGS=^
%MSVC_COMMON% ^
-MD ^
-GR ^
-Od ^
-Oi ^
-WX ^
-W4 ^
-FC ^
-Z7 ^
%MSVC_WARNINGS%

set INCLUDE_PATHS=^
-I ..\lib\ ^
-I F:\Dev_Tools\test_4coder\custom\


rem LINKER(MSVC)   
rem ============================================================
set LINKER_FLAGS=^
-incremental:no ^
-opt:ref

set LIBRARIES=^
User32.lib ^
Gdi32.lib ^
Dinput8.lib ^
Dxguid.lib ^
Opengl32.lib ^
Kernel32.lib ^
Ws2_32.lib ^
Shell32.lib


rem ************************************************************
rem **                       START BUILD                      **
rem ************************************************************
set path=F:\Dev\ColorCoder\build;%path%
pushd build

rem ==========                  EXE                   ==========     
rem ============================================================

cl %MSVC_FLAGS% %INCLUDE_PATHS% %SOURCES% /link %LIBRARIES%

popd
