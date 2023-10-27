@echo off
javac -sourcepath ./src/ -d ./bin/ -nowarn ./src/test/Test.java
echo this is a test executor and not meant for enduser
pause
java -cp ./bin/ test/Test
Pause