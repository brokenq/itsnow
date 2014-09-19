#!/bin/sh

#
# Usage: download.sh msc 0.1.7 path/to/file
# 

CI=ci.dnt.com.cn
if [ ! $1 ]; then
  echo "You should provide the artifact type: msc|msu|msp"
  exit 1
fi
if [ ! $2 ]; then
  echo "You should provide the artifact version(like 0.1.8) "
  exit 2
fi
utype=`echo "$1" | tr "[:lower:]" "[:upper:]"`
ltype=`echo "$1" | tr "[:upper:]" "[:lower:]"`
version=$2
output=
if [ $3 ]; then
 output="-O $3" 
fi

target=http://$CI/guestAuth/repository/download/Itsnow_Sprint_Build_$utype/.lastFinished/$ltype-$version.zip
echo "Downloading $target"
wget $target $output



