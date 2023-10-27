@echo off
javac -sourcepath ./src/ -d ./bin/ ./src/app/Main.java
pause
java -cp ./bin/ app/Main clean
Pause