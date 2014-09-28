#!/bin/sh

#
# Usage: prepare_ms.sh msc 0.1.7 path/to/folder
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
file=$ltype-$version.zip

if [[ "$version" =~ SNAPSHOT$ ]]; then
  target="http://$CI/guestAuth/repository/download/Itsnow_Continuous_Build/.lastFinished/$file"
else
  target="http://$CI/guestAuth/repository/download/Itsnow_Sprint_Build_$utype/.lastFinished/$file"
fi


if [ ! $3 ]; then
  folder="/opt/itsnow"
else
  folder=$3
fi
mkdir -p $folder

echo "Downloading $target"
wget $target -O $folder/$file

if [ $? -gt 0 ]; then
  echo "Failed to download $utype $version"
  exit 128
fi

cd $folder

zip_path=$(echo $file | sed s/\.zip//)
unzip $file -d $zip_path
chmod +x $zip_path/db/bin/migrate

mkdir -p downloads
mv *.zip downloads

# Ensure old link been deleted
rm -f $ltype

# Make the link to the version
ln -s $zip_path $ltype

echo "$ltype $version prepared"