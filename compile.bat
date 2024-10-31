SET JAVA_HOME=D:\applications\OpenJDK8U-jdk_x64_windows_hotspot_8u422b05\jdk8u422-b05
SET PATH=%JAVA_HOME%\bin

setlocal enabledelayedexpansion
for /r src\main\java %%f in (*.java) do (
    set "java_files=!java_files! %%f"
)
echo %java_files%
javac -d build\classes %java_files%
endlocal