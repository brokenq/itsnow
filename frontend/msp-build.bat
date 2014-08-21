@echo off
rmdir /q /s E:\opt\releases\itsnow\msp-0.1.5-SNAPSHOT\webapp\build
mkdir E:\opt\releases\itsnow\msp-0.1.5-SNAPSHOT\webapp\build
xcopy /e E:\project\insight\itsnow\frontend\build E:\opt\releases\itsnow\msp-0.1.5-SNAPSHOT\webapp\build
echo copy success!
exit