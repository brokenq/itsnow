#!/bin/sh

# Configure a new itsnow instance(msu/msp)
#   the host has been provisioned,
#    mysql/redis is ready
#   the msu|msp system has been created,
#    such as: msu has been extract to /opt/itsnow/msu-0.1.8 and linked as /opt/itsnow/msu
#   the new instance has been created,
#    such as: itsnow_msu_100 has been created at /opt/itsnow/itsnow_msu_100,
#      all links created, file need to be modified copied
#   the schema has been created
#      such as itsnow_msu_100 has been created for db user msu_001
# 
#  Usage  deploy.sh instance path/to/itsnow
#
#   java -jar lib/dnt.itsnow.release-x.y.z.jar path/to/instance resources/app.vars


script_dir=$(cd `dirname $0` && pwd )

if [ ! $1 ]; then
  echo "You should provide instance id as first argument"
  exit 1
fi
instance=$1
id=$(echo $instance | sed s/itsnow.\\?//)

if [ $2 ]; then
  folder=$2
else
  folder="/opt/itsnow"
fi
target=$folder/$instance
cd $target

java -jar lib/dnt.itsnow.release-*.jar $target .itsnow

echo "$folder/$instance deployed"

