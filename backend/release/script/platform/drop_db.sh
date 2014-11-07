#!/bin/sh

#
# Usage: drop_db.sh schema user
# 
#  schema:   the schema name, such as itsnow_msu_001
#  user:   the owner user name of this schema
#
if [ ! $1 ]; then
  echo "You should provide schema name as first argument"
  exit 1
fi
if [ ! $2 ]; then
  echo "You should provide owner user name of this schema as second argument"
  exit 2
fi
schema=$1
user=$2
mysql_pwd=`cat /root/.mysql_pwd`
mysql 2>/dev/null -uroot -p$mysql_pwd <<SQL
  DROP DATABASE $schema ;
  REVOKE SELECT ON itsnow_msc.* FROM '$user'@'%' ;
  DROP USER '$user'@'%' ;
  FLUSH PRIVILEGES;
SQL

if [ $? -eq 0 ]; then
  echo "Drop the database(schema) = $schema and user $user"
else
  echo "Failed to drop the database $schema and user $user"
  exit 1
fi
