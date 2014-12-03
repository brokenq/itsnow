#!/bin/sh

#
# Upgrade current msc|msu|msp release to a new version(maybe same version)
#
# Usage: upgrade.sh type version build
#
#  Steps:
#   1. download correpsonding version(such as 0.1.9-SNAPSHOT) from CI to itsnow folder( default: /opt/itsnow/downloads)
#   2. extract download zip to /opt/itsnow/msx-0.1.9-SNAPSHOT(if exists add suffix: .1/.2)
#   3. mkdir /op/itsnow/latest
#   4. link  /opt/itsnow/latest to /opt/itsnow/msx-0.1.9-SNAPSHOT 
#   5. stop current msx instance (in /opt/itsnow/msx)
#   6. backup/make snapshot for current
#   7. configure the new-system with current .itsnow properties
#   8. migrate db
#   9. start new msx again

CI=ci.itsnow.com
itsnow_dir=$(cd `dirname $0` && cd ../../../ && pwd )
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
  echo "You should provide system type msx|msu|msp as first argument"
  exit 1
fi
type=$1
current="$type"
utype=`echo $type | awk '{print toupper($1)}'`
if [ ! $2 ]; then
  echo "You should provide version as second argument"
  exit 1
fi
version=$2
if [ ! $3 ]; then
  build=".lastSuccessful"
else
  build="$3:id"
fi

cd $itsnow_dir
old_version=$(cat $type/bin/start.sh | grep APP_TARGET | head -1 | awk -Frelease\- '{print $2}')


folder="$type-$version"
if [ -d "$folder" ]; then
  last=$(last_of $folder)
  folder="$folder@$last"
fi
if [ -d "$folder" ]; then
  echo "The upgrading $itsnow_dir/$folder exist"
  exit 3
fi

file=$type-$version.zip

if [[ "$version" =~ SNAPSHOT$ ]]; then
  target="http://$CI/guestAuth/repository/download/Itsnow_Daily_Build_$utype/$build/$file"
else
  target="http://$CI/guestAuth/repository/download/Itsnow_Sprint_Build_$utype/$build/$file"
fi

# wget will override previous same version
echo "Step 1 downloading $target"
wget $target -O downloads/$file

if [ $? -gt 0 ]; then
  echo "Failed to download $target, check you version or CI site status/configuration please!"
  exit 4
fi

echo "Step 2 extract to $folder"
unzip downloads/$file -d $folder
mkdir -p $folder/logs $folder/tmp

echo "Step 3,4 Creating new-$type"
/bin/rm -rf $upgrading
ln -s $itsnow_dir/$folder $itsnow_dir/$upgrading
cp $current/.itsnow $upgrading

cd $itsnow_dir
chmod +x $upgrading/bin/*.sh $upgrading/bin/itsnow_$type $upgrading/db/bin/migrate  $upgrading/script/*/*.sh

echo "Step 5 stop current system"
cd $current
bin/itsnow_$type stop

echo "Step 6 backup $type"
script/platform/backup_db.sh itsnow_$type $itsnow_dir/backup $folder.sql
cd $itsnow_dir
last=$(last_of old)
/bin/mv -f $current old@$last

mv $upgrading $current
echo "$utype upgraded from $old_version to $version, link to $folder"

echo "Step 7 configure the new-system with current .itsnow properties"
cd $current
java -jar lib/dnt.itsnow.release-*.jar `pwd` .itsnow

echo "Step 8 migrate db"
cd $current/db
bin/migrate up
if [ $? -gt 0 ]; then
  echo "Failed to migrate new version, but try to start also"
fi

echo "Step 9 start new system"
cd $itsnow_dir/$current
bin/itsnow_$type start
bin/check.sh logs/wrapper.log itsnow_$type

cd $itsnow_dir

if [ $? -eq 0 ]; then
  echo "And new $utype started"
else
  echo "But new $utype failed to start"
  exit 128
fi
