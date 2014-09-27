#!/bin/sh

#
# Interpolate properties file by variables
#
#  Usage: interpolate.sh now.properties variables.properties
#
if [ ! $1 ]; then
  echo "You should provide target properties file as first argument"
  exit 1
fi
if [ ! $2 ]; then
  echo "You should provide variables properties file as second argument"
  exit 2
fi
target=$1
vars=$2

interpolate(){
  key=$1
  value=$2
  value=$(echo $value | sed s/\\//\\\\\\//g)
  #echo "interpolate $target with $key $value"
  sed -i "s/$key=[a-zA-Z0-9_|\\-]\+/$key=$value/g" $target
}

for line in $(<$vars)
do
  key=`echo $line | awk -F= '{print $1}'`
  value=`echo $line | awk -F= '{print $2}'`
  interpolate $key $value
done
 
