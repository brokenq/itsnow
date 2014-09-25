#!/bin/bash

#
# 启动业务进程
#
# Usage: start_ms.sh target path/to/itsnow(optional)
#
#  1. cd target/path/
#  2. clean logs/wrapper.log
#  3. bin/itsnow-$id start
#  4. check logs/wrapper.log

if [ ! $1 ]; then
  echo "You should provide instance id as first argument"
  exit 1
fi
instance=$1
id=$(echo $instance | sed s/itsnow.\\?//)
launcher="bin/itsnow-$id"

if [ $2 ]; then
  folder=$2
else
  folder="/opt/itsnow"
fi

cd $folder/$instance
/bin/rm -f logs/wrapper.log

$launcher start

if [ $? -gt 0 ]; then
  echo "Can't start the $instance, please check the status"
  exit $?
fi

bin/check.sh logs/wrapper.log $instance