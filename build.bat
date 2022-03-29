@echo off
cmd.exe /C requirements.bat
set retCode=%ERRORLEVEL%
IF %retCode% NEQ 0 (
    ECHO Cannot find necessary libraries.
    EXIT /B %retCode%
)

dir /s /B *.java > sources1.txt
findstr /v /i "test" sources1.txt > sources2.txt
DEL sources1.txt
javac -encoding UTF-8 -classpath .\jna-5.10.0.jar @sources2.txt -d out\production\tictactoe


set retCode=%ERRORLEVEL%
IF %retCode% NEQ 0 (
    ECHO Failed to build java.
    EXIT /B %retCode%
)

DEL sources2.txt