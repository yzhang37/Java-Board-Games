@echo off
set path1=jna-5.10.0.jar

if not exist %path1% (
    curl.exe "https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.10.0/%path1%" -o "%path1%"
)

