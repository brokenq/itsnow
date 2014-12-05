@ECHO OFF

SET APP_HOME=%~dp0..
SET APP_NAME=#{app.name}
SET APP_PORT=#{app.rmi.port}

SET BOOTSTRAP_JAR=boot/net.happyonroad.spring-component-framework-#{app.fwkVersion}.jar
SET APP_TARGET=dnt.itsnow.release-#{app.version}
SET OPTIONS=-Dlogback.configurationFile=config/logback.xml

@ECHO ON
CD %APP_HOME%
java -Dapp.home=%APP_HOME% ^
     -Dapp.name=%APP_NAME% ^
     -Dapp.port=%APP_PORT% ^
     %OPTIONS%             ^
     -jar %BOOTSTRAP_JAR%  ^
     %APP_TARGET%          ^
     --stop