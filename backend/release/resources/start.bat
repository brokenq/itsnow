@ECHO OFF

SET APP_HOME=%~dp0..
SET APP_NAME=#{app.name}
SET APP_PORT=#{app.rmi.port}

SET BOOTSTRAP_JAR=boot/net.happyonroad.spring-component-framework-#{app.fwkVersion}.jar
SET APP_TARGET=dnt.itsnow.release-#{app.version}
SET OPTIONS=-Dlogback.configurationFile=config/logback.xml

@ECHO ON
cd %APP_HOME%
java -Dapp.home=%APP_HOME% ^
     -Dapp.name=%APP_NAME% ^
     -Dapp.port=%APP_PORT% ^
     %OPTIONS%             ^
     -Dcomponent.feature.resolvers=dnt.itsnow.platform.services.MybatisFeatureResolver,dnt.itsnow.platform.services.SpringMvcFeatureResolver ^
     -Xms128m -Xmx256m -XX:MaxPermSize=256m -Djava.awt.headless=true ^
     -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=#{app.debug.port} ^
     -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=#{app.jmx.port} ^
     -Dcom.sun.management.jmxremote.local.only=false   ^
     -Dcom.sun.management.jmxremote.authenticate=false ^
     -Dcom.sun.management.jmxremote.ssl=false          ^
     -jar %BOOTSTRAP_JAR%  ^
     %APP_TARGET%