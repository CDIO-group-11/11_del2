@echo off
javac -sourcepath ./src/ -d ./bin/ -nowarn ./src/app/Main.java
echo please try run.bat as it supports more features
pause 
java -cp ./bin/ app/Main clean
Pause