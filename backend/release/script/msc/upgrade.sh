#!/bin/sh

#
# Upgrade current msc release to a new version(maybe same version)
#
# Usage: upgrade.sh version
#
#  Steps:
#   1. download correpsonding version(such as 0.1.9-SNAPSHOT) from CI to itsnow folder( default: /opt/itsnow/downloads)
#   2. extract download zip to /opt/itsnow/msc-0.1.9-SNAPSHOT(if exists add suffix: .1/.2)
#   3. mkdir /op/itsnow/new-msc/
#   4. link  /opt/itsnow/new-msc/* to /opt/itsnow/msc-0.1.9-SNAPSHOT/* except webapp
#   5. cp /opt/itsnow/msc-0.1.9-SNAPSHOT/webapp to /opt/itsnow/new-msc/(override)
#   6. replace change files(from current itsnow)
#   7. replace /opt/system/config with neweast one
#   8. stop current msc instance (in /opt/itsnow/msc)
#   9. make snapshot for current db
#   10. migrate db
#   11. mv /opt/itsnow/msc to /opt/itsnow/old-msc
#   12. mv /opt/itsnow/new-msc to /opt/itsnow/msc
#   13.start new msc again

CI=ci.itsnow.com
itsnow_dir=$(cd `dirname $0` && cd ../../../ && pwd )
current="msc"
upgrading="latest"
cp="/bin/cp -f"


function last_of(){
  last=$(ls . | grep $1 | awk -F@ '{print $2}' | sort -n | tail -1)
  if [ "$last" == "" ]; then
    last="1"
  else
    let last=$last+1
  fi
  echo $last
}

# /opt/itsnow/ as usual

if [ ! $1 ]; then
  echo "You should provide upgrading version as first argument"
  exit 1
fi
version=$1
if [ ! $2 ]; then
  build=".lastFinished"
else
  build="$2:id"
fi

cd $itsnow_dir
old_version=$(cat msc/bin/start.sh | grep APP_TARGET | head -1 | awk -Frelease\- '{print $2}')


folder="msc-$version"
if [ -d "$folder" ]; then
  last=$(last_of $folder)
  folder="$folder@$last"
fi
if [ -d "$folder" ]; then
  echo "The upgrading $itsnow_dir/$folder exist"
  exit 3
fi

file=msc-$version.zip

if [[ "$version" =~ SNAPSHOT$ ]]; then
  target="http://$CI/guestAuth/repository/download/Itsnow_Daily_Build_MSC/$build/$file"
else
  target="http://$CI/guestAuth/repository/download/Itsnow_Sprint_Build_MSC/$build/$file"
fi

# wget will override previous same version
echo "Step 1 downloading $target"
wget $target -O downloads/$file

if [ $? -gt 0 ]; then
  echo "Failed to download $target, check you version or CI site status/configuration please!"
  echo 4
fi

echo "Step 2 extract to $folder"
unzip downloads/$file -d $folder
mkdir -p $folder/logs $folder/tmp

echo "Step 3,4,5 Creating new-msc"
/bin/rm -rf $upgrading
mkdir $upgrading

cd $folder
for file in *
do
  if [[ $file =~ webapp$ ]]; then
    cp -r $file $itsnow_dir/$upgrading
  else
    ln -s $itsnow_dir/$folder/$file $itsnow_dir/$upgrading/$file
  fi
done

echo "Step 6 replace change files(from current itsnow)"
cd $itsnow_dir

change_list="bin/start.sh bin/stop.sh bin/itsnow-msc config/logback.xml config/nginx.conf config/now.properties config/wrapper.conf db/migrate/environments/production.properties"
for file in $change_list; do
  $cp $current/$file $upgrading/$file
  if [ "$version" != "$old_version" ]; then
    sed -i s/$old_version/$version/g $upgrading/$file
  fi
done

echo "Step 7 update /etc and /opt/system/config"
$cp /etc/redis.conf /etc/redis.conf.bak
$cp $upgrading/resources/redis-master.conf /etc/redis.conf

$cp /usr/my.cnf /usr/my.cnf.bak
$cp $upgrading/resources/my-master.cnf /usr/my.cnf
$cp $upgrading/resources/my-slave.cnf  /opt/system/config/my.conf
# service mysql restart


$cp $upgrading/resources/redis /opt/system/config
$cp $upgrading/resources/redis-slave.conf /opt/system/config/redis.conf

chmod +x $upgrading/bin/*.sh $upgrading/bin/itsnow-msc $upgrading/db/bin/migrate  $upgrading/script/*/*.sh

echo "Step 8 stop current msc"
cd $current
bin/itsnow-msc stop
echo "Step 9 backup db"
script/platform/backup_db.sh itsnow_msc $itsnow_dir/backup $folder.sql

cd $itsnow_dir
last=$(last_of old)
/bin/mv -f $current old@$last


echo "Step 10 migrate db"
mv $upgrading $current
echo "MSC upgraded from $old_version to $version, link to $folder"

cd $current/db
bin/migrate --env=production up
if [ $? -gt 0 ]; then
  echo "Failed to migrate new version, but try to start also"
fi

echo "Step 13 start new msc"
cd $itsnow_dir/$current
bin/itsnow-msc start
bin/check.sh logs/wrapper.log Itsnow-msc

cd $itsnow_dir

if [ $? -eq 0 ]; then
  echo "And new msc started"
else
  echo "But new msc failed to start"
  exit 128
fi