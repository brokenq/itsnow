#!/bin/bash

#
# Usage prepare_deploy.sh {msu|msp} instance path/to/itsnow
#
#  create a new instance folder from current release: $type
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
id=$(echo $instance | sed s/itsnow.\\?//)

if [ -d instance ]; then
  echo "The $instance has been created!"
  exit 3
fi

if [ $3 ]; then
  folder=$3
else
  folder="/opt/itsnow"
fi

cd $folder
mkdir -p $instance/config $instance/logs $instance/bin $instance/db/migrate/environments

echo "Prepare bin folder for $instance"
for file in $(ls $type/bin/wrapper*); do
  part=$(echo $file | sed s/$type//)
  ln -s $folder/$file $instance$part
done
cp $folder/$type/bin/itsnow-* $instance/bin/itsnow-$id
cp $folder/$type/bin/*.sh $instance/bin/
chmod +x $instance/bin/*.sh

echo "Prepare db folder for $instance"
db_list="db/bin db/lib db/LICENSE db/NOTICE db/migrate/drivers db/migrate/scripts db/migrate/README "
for file in $db_list; do
  ln -s $folder/$type/$file    $instance/$file
done
cp $folder/$type/db/migrate/environments/* $instance/db/migrate/environments
cp $folder/$type/db/migrate/environments/development.properties $instance/db/migrate/environments/production.properties

cp -r $type/config/* $instance/config/
base_list="boot lib script repository webapp"
for file in $base_list; do
  ln -s $folder/$type/$file    $instance/$file
done

echo "$folder/$instance created"