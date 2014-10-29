#!/bin/sh

#
#  Usage  migrate_ms.sh instance path/to/itsnow(optional)

if [ ! $1 ]; then
  echo "You should provide instance id as first argument"
  exit 1
fi
instance=$1
if [ $2 ]; then
  folder=$2
else
  folder="/opt/itsnow"
fi

echo "Migrating for $instance"

cd $folder/$instance/db
chmod +x bin/migrate
bin/migrate --env=production up

if [ $? -eq 0 ]; then
  echo "Migrated for $instance"
else
  echo "Error while migrating for $instance "
  exit $?
fi