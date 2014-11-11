#!/bin/bash

#
# 停止业务进程
#
# Usage: stop_ms.sh target  path/to/itsnow(optional)
#
#  1. cd target/path/
#  2. bin/itsnow-$target stop
#  3. check logs/wrapper.log

if [ ! $1 ]; then
  echo "You should provide instance id as first argument"
  exit 1
fi
instance=$1
id=$(echo $instance | sed s/itsnow.\\?//)
launcher="bin/itsnow_$id"

if [ $2 ]; then
  folder=$2
else
  folder="/opt/itsnow"
fi

cd $folder/$instance

$launcher stop
