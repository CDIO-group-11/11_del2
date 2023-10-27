@echo off
javac -sourcepath ./src/ -d ./bin/ -nowarn ./src/app/Main.java
echo if there are problems with wierd characters/overlapping text, you shoud use runClean.bat instead
pause
java -cp ./bin/ app/Main
Pause