@echo off
rmdir /q /s E:\opt\releases\itsnow\msc-0.1.3-SNAPSHOT\webapp\build
mkdir E:\opt\releases\itsnow\msc-0.1.3-SNAPSHOT\webapp\build
xcopy /e E:\project1\insight\itsnow\frontend\build E:\opt\releases\itsnow\msc-0.1.3-SNAPSHOT\webapp\build
echo ¸´ÖÆ³É¹¦
exit