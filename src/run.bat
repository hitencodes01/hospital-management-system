@echo off
javac App.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    exit /b
)
java App