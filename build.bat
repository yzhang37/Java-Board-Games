@echo off
cmd.exe /C requirements.bat
set retCode=%ERRORLEVEL%
IF %retCode% NEQ 0 (
    ECHO Cannot find necessary libraries.
    EXIT /B %retCode%
)

dir /s /B *.java > sources.txt
javac -encoding UTF-8 -classpath .\jna-5.10.0.jar @sources.txt -d out\production\tictactoe

set retCode=%ERRORLEVEL%
IF %retCode% NEQ 0 (
    ECHO Failed to build java.
    EXIT /B %retCode%
)

DEL sources.txt