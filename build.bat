@ECHO OFF
IF NOT EXIST build MKDIR build


REM REFERENCES:https://docs.microsoft.com/en-us/cpp/build/reference/compiler-options-listed-alphabetically?view=vs-2019

REM ************************************************************
REM ************************************************************
REM **                                                        **
REM **                       DEFINITIONS                      **
REM **                                                        **
REM ************************************************************
REM ************************************************************

REM ==========              BUILD OPTIONS             ==========     
REM ============================================================
SET PLATFORM=%1
SET OUTPUT=%2

REM ==========              PROJECT/FILES             ==========     
REM ============================================================
SET PROJECT_NAME=ColorCoder
SET SOURCES= ..\src\Win32_%PROJECT_NAME%.c


REM ==========              COMPILER(MSVC)            ==========     
REM ============================================================
SET MSVC_COMMON=^
-nologo

REM TODO(MIGUEL): ENABLE WARNINGS ONE BY ONE AND RESOLVE
SET MSVC_WARNINGS=^
-wd4057 ^
-wd4013 ^
-wd4057 ^
-wd4100 ^
-wd4101 ^
-wd4189 ^
-wd4204 ^
-wd4244 ^
-wd4456 ^
-wd4700 ^
-wd4706 ^
-wd4996

REM NOTE(MIGUEL):-MD is using Dynamic CRT Lib which is what is supporting the console
REM TODE(MIGUEL): Figure out a way to get a console that doesnt need to use the MD flag

SET MSVC_FLAGS=^
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

SET INCLUDE_PATHS=^
-I ..\lib\ ^
-I F:\Dev_Tools\test_4coder\custom\


REM ==========             LINKER(MSVC)               ==========     
REM ============================================================
SET LINKER_FLAGS=^
-incremental:no ^
-opt:ref

SET LIBRARIES=^
User32.lib ^
Gdi32.lib ^
Dinput8.lib ^
Dxguid.lib ^
Opengl32.lib ^
Kernel32.lib ^
Ws2_32.lib ^
Shell32.lib


REM ************************************************************
REM ************************************************************
REM **                                                        **
REM **                       START BUILD                      **
REM **                                                        **
REM ************************************************************
REM ************************************************************
PUSHD build
REM PATH=%PATH%;F:\Dev\ColorCoder\build

IF %Platform%==--win (CALL :WINDOWS)
IF %Platform%==--lin (CALL :LINUX  )
IF %Platform%==--mac (CALL :MAC    )

POPD
EXIT /B 0


ECHO ==========             COMPILE AND LINK           ==========     
ECHO ============================================================

:WINDOWS
ECHO ====================      WINDOWS       ====================
ECHO ============================================================
CALL "C:\Program Files (x86)\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvarsall.bat" x64

IF %OUTPUT%==--exe       (CALL :COMPILE_WIN_EXE)

IF %OUTPUT%==--dll       (CALL :COMPILE_WIN_DLL)

IF %OUTPUT%==--dllinject (CALL :COMPILE_WIN_DLL_INJECTOR)

EXIT

ECHO ==========             CREATE AN EXE              ==========     
ECHO ============================================================
:COMPILE_WIN_EXE
CALL cl %MSVC_FLAGS% %INCLUDE_PATHS% %SOURCES% /link %LIBRARIES%
EXIT


ECHO ==========              CREATE A DLL              ==========     
ECHO ============================================================
:COMPILE_WIN_DLL
CALL cl -nologo -MD -Zi %INCLUDE_PATHS% ..\src\ColorCoder.c -FmColorCoder.map  /LD /link -DLL -EXPORT:app_update
EXIT


ECHO ==========         CREATE A DLL(INJECTION)        ==========     
ECHO ============================================================
:COMPILE_WIN_DLL_INJECTOR
CALL cl -nologo -MD -Zi %INCLUDE_PATHS% ..\src\4coder_theme_loader.c ..\src\hooks_manager.c -Fm4coder_theme_loader.map  /LD /link -DLL -EXPORT:DllMain
EXIT

POPD
