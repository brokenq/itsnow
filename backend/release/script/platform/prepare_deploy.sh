#!/bin/bash

#
# Usage prepare_deploy.sh {msu|msp} instance
#
#  create a new instance folder from current release: /opt/itsnow/$type
#


if [ ! $1 ]; then
  echo "You should provide system type: msu or msp as first argument"
  exit 1
fi

type=$1

if [ ! $2 ]; then
  echo "You should provide instance name as second argument"
  exit 2
fi
instance=$2

if [ -d instance ]; then
  echo "The $instance has been created!"
  exit 3
fi

mkdir /opt/itsnow/$instance
cd /opt/itsnow/$instance
cp -r /opt/itsnow/$type/bin /opt/itsnow/$type/config /opt/itsnow/$type/db .
mkdir logs
ln -s /opt/itsnow/$type/boot       boot
ln -s /opt/itsnow/$type/lib        lib
ln -s /opt/itsnow/$type/script     script
ln -s /opt/itsnow/$type/repository repository
ln -s /opt/itsnow/$type/webapp     webapp
