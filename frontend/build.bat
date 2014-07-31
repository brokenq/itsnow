@echo off
rmdir /q /s E:\opt\releases\itsnow\itsnow-0.1.2-SNAPSHOT\webapp\build
mkdir E:\opt\releases\itsnow\itsnow-0.1.2-SNAPSHOT\webapp\build
xcopy /e E:\projects\insight\itsnow\frontend\build E:\opt\releases\itsnow\itsnow-0.1.2-SNAPSHOT\webapp\build
echo ¸´ÖÆ³É¹¦
exit