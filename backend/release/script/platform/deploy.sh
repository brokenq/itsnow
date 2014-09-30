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
#   1. interpolate config/now.properties (app.id, http.port,  mysql host/port/user/password, redis host/port/index)
#   2. create and interpolate db/migrate/environments/production.properites
#   3. interpolate bin/*.sh
#   4. interpolate config/wrapper.conf bin/itsnow-$id


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

function interpolate_file(){
  if [ $2 ]; then
    vars=$2
  else
    vars=$(echo $1 | sed s/\\..*$/.vars/)
  fi
  if [ $3 ]; then
     intp=$script_dir/$3
  else
     intp=$script_dir/interpolate.sh
  fi
  if [ -f $vars ]; then
    echo "Interpolating $1 by $vars"
    /bin/cp -f $1 $1.bak
    $intp $1 $vars
  else
    echo "There is no $vars for $1"
  fi
}

cd $folder/$instance

interpolate_file config/now.properties
interpolate_file db/migrate/environments/production.properties
interpolate_file bin/start.sh bin/sh.vars
interpolate_file bin/stop.sh bin/sh.vars
interpolate_file config/wrapper.conf
interpolate_file bin/itsnow-$id config/wrapper.vars
interpolate_file config/nginx.conf config/nginx.vars interpolate2.sh

echo "$folder/$instance deployed"

