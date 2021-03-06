#!/bin/sh

# Undeploy a itsnow instance(msu/msp)
#   the host has been provisioned,
#
#  Usage  deploy.sh instance path/to/itsnow
#


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


# /bin/rm -f $folder/$instance
# do not real deleted while testing
/bin/rm -f $folder/deleted_$id
/bin/mv -f $folder/$instance $folder/deleted_$id

echo "$folder/$instance is removed!"