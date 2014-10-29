#!/bin/sh

#
# Upgrade current msc release to a new version(maybe same version)
#
# Usage: upgrade.sh version
#
#  Steps:
#   1. download correpsonding version(such as 0.2.1) from CI to itsnow folder( default: /opt/itsnow/downloads)
#   2. find all msu apps in /opt/itsnow/
#   3.   extract download zip to /opt/itsnow/msu-0.2.1@1(if exists add suffix: .1/.2)
#   4.   create temp dir and copy exist config file to it(/opt/itsnow/lastest_msu)
#   5.   replace change files
#   6.   update config
#   7.   stop msu app
#   8.   backup msu db
#   9.   move current to old and move temp to current
#   10.  migrate msu db
#   11.  start msu app

CI=ci.itsnow.com
itsnow_dir=/opt/itsnow
#itsnow_dir=$(cd `dirname $0` && cd ../../../ && pwd )
app="msu"
upgrading="latest_msu"
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

file_download=msu-$version.zip

if [[ "$version" =~ SNAPSHOT$ ]]; then
  target="http://$CI/guestAuth/repository/download/Itsnow_Continuous_Build/.lastFinished/$file_download"
else
  target="http://$CI/guestAuth/repository/download/Itsnow_Sprint_Build_MSU/.lastFinished/$file_download"
fi
# wget will override previous same version
echo "Step 1 downloading $target"
wget $target -O $itsnow_dir/downloads/$file_download

if [ $? -gt 0 ]; then
  echo "Failed to download $target, check you version or CI site status/configuration please!"
  exit 4
fi

echo "Step 2 find all msu apps in $itsnow_dir"
cd $itsnow_dir
itsnow_files=$(ls -l |grep itsnow_ | awk  '{print $9}')
echo $itsnow_files
for file in $itsnow_files
do
  type=$(ls -l $itsnow_dir/$file/boot | awk -F\-\> '{print $2}' | awk -F/ '{print $4}' | awk -F\- '{print $1}')
  if [[ $type =~ msu$ ]]; then
    echo "=====================begin upgrade msu:$file=============================================="
 
    cd $itsnow_dir/$file
    old_version=$(cat bin/start.sh | grep APP_TARGET | head -1 | awk -Frelease\- '{print $2}')
    cd $itsnow_dir
 
    folder="msu-$version"
    if [ -d "$folder" ]; then
      last=$(last_of $folder)
      folder="$folder@$last"
    fi
    if [ -d "$folder" ]; then
      echo "The upgrading $itsnow_dir/$folder exist"
      exit 3
    fi

    echo "Step 3 extract to $folder"
    cd $itsnow_dir
    unzip $itsnow_dir/downloads/$file_download -d $folder
    mkdir -p $folder/logs $folder/tmp

    echo "Step 4 create temp dir and copy exist config file to it"
    /bin/rm -rf $itsnow_dir/$upgrading
    mkdir $itsnow_dir/$upgrading

    cd $itsnow_dir/$folder
    for f in *
    do
      if [[ $f =~ webapp$ ]]; then
        cp -r $f $itsnow_dir/$upgrading
      else
        ln -s $itsnow_dir/$folder/$f $itsnow_dir/$upgrading/$f
      fi
    done

    cd $itsnow_dir/$file
    app=$(pwd |awk -Fitsnow_ '{print $2}')

    echo "Step 5 replace change files(from current itsnow)"

    change_list="bin/start.sh bin/stop.sh bin/itsnow-$app config/logback.xml config/nginx.conf config/now.properties config/wrapper.conf db/migrate/environments/production.properties"
    for f in $change_list; do
      $cp $f $itsnow_dir/$upgrading/$f
      if [ "$version" != "$old_version" ]; then
        sed -i s/$old_version/$version/g $itsnow_dir/$upgrading/$f
      fi
    done

    echo "Step 6 update config"
    cd $itsnow_dir
    chmod +x $upgrading/bin/*.sh $upgrading/bin/itsnow-$app $upgrading/db/bin/migrate  $upgrading/script/*/*.sh

    echo "Step 7 stop $app msu"
    cd $itsnow_dir/itsnow_$app
    bin/itsnow-$app stop

    echo "Step 8 backup $app db"
    cd $itsnow_dir/itsnow_$app
    script/platform/backup_db.sh itsnow_$app $itsnow_dir/backup $app-$folder.sql

    echo "Step 9 move current to old and move temp to current"
    cd $itsnow_dir
    last=$(last_of old)
    /bin/mv -f itsnow_$app old@$last
    /bin/mv -f $upgrading itsnow_$app
    echo "MSU upgraded from $old_version to $version, link to $folder"

    echo "Step 10 migrate db"
    cd $itsnow_dir/itsnow_$app/db
    bin/migrate --env=production up
    if [ $? -gt 0 ]; then
      echo "Failed to migrate new version, but try to start also"
    fi

    echo "Step 11 start $app msu"
    cd $itsnow_dir/itsnow_$app
    bin/itsnow-$app start
    bin/check.sh logs/wrapper.log Itsnow_$app

    cd $itsnow_dir

    if [ $? -eq 0 ]; then
      echo "And new $app msu started"
    else
      echo "But new $app msu failed to start"
    fi  

    echo "=====================upgrade msu:$file finish!=============================================="

  fi
done

exit 128

