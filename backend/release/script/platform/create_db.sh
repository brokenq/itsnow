#!/bin/sh

#
# Usage: create_db.sh dbname user password
# 
#  schema:   the schema name, such as itsnow_msu_001
#  user  :   the mysql user which will use this database
#               this user will be assigned will full privleges on this db
#               and he will get read privileges to itsnow_msc
#  password: the password for this mysql user
#
if [ ! $1 ]; then
  echo "You should provide schema name as first argument"
  exit 1
fi
if [ ! $2 ]; then
  echo "You should provide user name as second argument"
  exit 2
fi
if [ ! $3 ]; then
  echo "You should provide password as third argument"
  exit 3
fi
schema=$1
user=$2
password=$3
mysql_pwd=`cat /root/.mysql_pwd`
mysql 2>/dev/null -uroot -p$mysql_pwd <<SQL
  CREATE DATABASE $schema DEFAULT CHARACTER SET UTF8;
  CREATE USER '$user'@'%' IDENTIFIED BY '$password';
  GRANT ALL ON $schema.* TO '$user'@'%';
  GRANT SELECT ON itsnow_msc.* TO '$user'@'%';
  FLUSH PRIVILEGES;
SQL

if [ $? -eq 0 ]; then
  echo "Created a new database(schema) = $schema for user $user"
else
  echo "Failed to create the database $schema for user $user"
  exit 1
fi
