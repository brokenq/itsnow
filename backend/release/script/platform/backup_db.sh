#!/bin/sh
#
# Usage:  backup_db.sql schema path/to/store
#   
# Backup the mysql database to specified path
#

if [ ! $1 ]; then
  echo "You must specify the schema name as first argument"
  exit 1
fi
schema=$1

# Local dir store exported data
if [ ! $2 ]; then
  dir="/opt/itsnow/backup/db"
else 
  dir=$2
fi
# Today db file name
today=`date +%Y_%m_%d`
file="$dir/$today-$schema.gz"
mysql_pwd=`cat /root/.mysql_pwd`

echo "Backup $schema $today"
echo "Mysql exporting $schema -> $file ..."
mysqldump -uroot -p$mysql_pwd $schema | gzip > $file
echo "Mysql exported  $schema -> $file ..."

