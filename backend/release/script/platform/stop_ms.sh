#!/bin/bash

#
#                   
#
# Usage: stop_ms.sh target process-port  path/to/itsnow(optional)
#
#  1. cd target/path/
#  2. bin/itsnow-$target stop
#  3. check logs/wrapper.log

if [ ! $1 ]; then
  echo "You should provide instance id as first argument"
  exit 1
fi

if [ ! $2 ]; then
    echo "You should provide process port as second argument"
    exit 1
fi

instance=$1
port=$2
id=$(echo $instance | sed s/itsnow.\\?//)
launcher="bin/itsnow_$id"

if [ $3 ]; then
  folder=$3
else
  folder="/opt/itsnow"
fi

cd $folder/$instance

$launcher stop

bin/check_stop.sh $port


