#!/bin/sh

#
# Lock mysql table and perform a script
#
#  Usage lock.sh script/to/perform
#

if [ ! $1 ]; then
  echo "You must specify the script to be executed as first argument"
  exit 1
fi
script=$1
password=`cat ~/.mysql_pwd`

mysql -uroot -p$password <<SQL
  FLUSH TABLES WITH READ LOCK;
  SYSTEM $script
  UNLOCK TABLES;
SQL