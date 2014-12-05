#! /bin/sh

APP_HOME=$(cd `dirname $0`; cd ..; pwd)
APP_NAME=#{app.name}
APP_PORT=#{app.rmi.port}

BOOTSTRAP_JAR=boot/net.happyonroad.spring-component-framework-#{app.fwkVersion}.jar
APP_TARGET=dnt.itsnow.release-#{app.version}
OPTIONS=-Dlogback.configurationFile=config/logback.xml

cd $APP_HOME
java -Dapp.home=$APP_HOME \
     -Dapp.name=$APP_NAME \
     -Dapp.port=$APP_PORT \
     $OPTIONS             \
     -Dcomponent.feature.resolvers=net.happyonroad.platform.services.MybatisFeatureResolver,net.happyonroad.platform.services.SpringMvcFeatureResolver \
     -Xms128m -Xmx256m -XX:MaxPermSize=256m -Djava.awt.headless=true \
     -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=#{app.debug.port} \
     -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=#{app.jmx.port} \
     -Dcom.sun.management.jmxremote.local.only=false \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false       \
     -jar $BOOTSTRAP_JAR  \
     $APP_TARGET