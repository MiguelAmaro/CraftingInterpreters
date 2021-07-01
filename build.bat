@echo off
if not exist build mkdir build


rem REFERENCES:https://docs.microsoft.com/en-us/cpp/build/reference/compiler-options-listed-alphabetically?view=vs-2019

rem ************************************************************
rem ************************************************************
rem **                                                        **
rem **                       DEFINITIONS                      **
rem **                                                        **
rem ************************************************************
rem ************************************************************

rem ==========              BUILD OPTIONS             ==========     
rem ============================================================
set PLATFORM=%1
set OUTPUT=%2

rem ==========              PROJECT/FILES             ==========     
rem ============================================================
set PROJECT_NAME=mylang
set SOURCES= ..\src\Win32_%PROJECT_NAME%.c


rem ==========              COMPILER(MSVC)            ==========     
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


rem ==========             LINKER(MSVC)               ==========     
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
rem ************************************************************
rem **                                                        **
rem **                       START BUILD                      **
rem **                                                        **
rem ************************************************************
rem ************************************************************
pushd build
rem PATH=%PATH%;F:\Dev\ColorCoder\build

if %Platform% equ --win (call :WINDOWS)
if %Platform% equ --lin (call :LINUX  )
if %Platform% equ --mac (call :MAC    )

popd
exit /B 0


echo ==========             COMPILE AND LINK           ==========     
echo ============================================================

:WINDOWS
echo ====================      WINDOWS       ====================
echo ============================================================
call "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvarsall.bat" x64

if %OUTPUT%==--exe       (call :COMPILE_WIN_EXE)

if %OUTPUT%==--dll       (call :COMPILE_WIN_DLL)

if %OUTPUT%==--dllinject (call :COMPILE_WIN_DLL_INJECTOR)

exit

echo ==========             CREATE AN EXE              ==========     
echo ============================================================
:COMPILE_WIN_EXE
call cl %MSVC_FLAGS% %INCLUDE_PATHS% %SOURCES% /link %LIBRARIES%
exit


echo ==========              CREATE A DLL              ==========     
echo ============================================================
:COMPILE_WIN_DLL
call cl -nologo -MD -Zi %INCLUDE_PATHS% ..\src\ColorCoder.c -FmColorCoder.map  /LD /link -DLL -EXPORT:app_update
exit


echo ==========         CREATE A DLL(INJECTION)        ==========     
echo ============================================================
:COMPILE_WIN_DLL_INJECTOR
call cl -nologo -MD -Zi %INCLUDE_PATHS% ..\src\4coder_theme_loader.c ..\src\hooks_manager.c -Fm4coder_theme_loader.map  /LD /link -DLL -EXPORT:DllMain
exit

popd
