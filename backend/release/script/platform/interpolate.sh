#!/bin/sh

#
# Interpolate one properties file into another
#
#  Usage: interpolate.sh new.properties now.properties
#
if [ ! $1 ]; then
  echo "You should provide new properties file as first argument"
  exit 1
fi
if [ ! $2 ]; then
  echo "You should provide target properties file as second argument"
  exit 2
fi
new=$1
target=$2

interpolate(){
  key=$1
  value=$2
  #echo "interpolate $target with $key $value"
  sed -i "s/$key=.*/$key=$value/g" $target
}

for line in $(<$new) 
do
  key=`echo $line | awk -F= '{print $1}'`
  value=`echo $line | awk -F= '{print $2}'`
  interpolate $key $value
done
 
