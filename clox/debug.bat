@echo off
if not exist debug mkdir debug

set OPTION=%1
set DEBUG_FILE= .\debug\debug.rdbg
set EXE= .\build\main.exe


if "%OPTION%" equ "-rdbg" (goto :REMEDY)
goto :REMEDY rem !!!DEFAULT PATH!!!

:REMEDY
call F:\Dev_Tools\RemedyBG\release_0.3.7.1\remedybg.exe -g -q %DEBUG_FILE%
goto eof

:eof
pause



