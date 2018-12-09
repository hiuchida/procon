@echo off

set CP=../../java/bin
set MAIN=Main
set IN_PATH=..\..\data\in

cd ..
if not exist tmp (
md tmp
cd tmp
)

call :check q1
call :check read
call :check write
call :check append_col
call :check substr
call :check concat1
call :check concat2
call :check select_col
call :check select_row
call :check count1
call :check count2
call :check min1
call :check min2
call :check max1
call :check max2
call :check limit001
call :check limit101
call :check limit201
call :check limit301
call :check combined

exit /b 0

:check
if not exist %1 (
md %1
md %1\csv
md %1\dump
)
cd %1
del /q csv\* dump\*

if exist %IN_PATH%\%1.txt (
echo execute %1
"%JAVA_HOME%\bin\java" -cp %CP% %MAIN% < %IN_PATH%\%1.txt > %1.txt 2>&1
)
cd ..
exit /b 0
