@echo off
REM ================================================================
REM Script Name: append_command.bat
REM Description: This script takes a single string as an argument,
REM              appends it to a predefined base command, and 
REM              executes the complete command dynamically. 
REM Usage:       append_command.bat "your_argument_here"
REM Example:     pypip.bat pandas => python -m pip install pandas 
REM Notes:       - Ensure the base command is defined correctly in the script.
REM              - Enclose arguments with spaces in double quotes.
REM ================================================================

@echo off
REM Check if an argument is provided
if "%~1"=="" (
    echo Usage: append_command.bat "your_argument_here"
    exit /b 1
)

REM Define the base command
set base_command=python -m pip install

REM Append the argument to the base command
set full_command=%base_command% %~1

REM Echo the full command to show what is being executed
echo Executing: %full_command%

REM Execute the command
%full_command%
