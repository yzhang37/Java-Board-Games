@echo off
cmd.exe /C requirements.bat
set retCode=%ERRORLEVEL%
IF %retCode% NEQ 0 (
    ECHO Cannot find necessary libraries.
    EXIT /B %retCode%
)

java -classpath out\production\tictactoe;jna-5.10.0.jar club.denkyoku.tictactoe.Main
