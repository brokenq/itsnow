#!/bin/sh

#
# Usage: assign_user_group.sh db db_user db_pwd user [group]
#
#  user : the user's username
#  group: the group  name
#
if [ ! $1 ]; then
  echo "You should provide db name as first argument"
  exit 1
fi
if [ ! $2 ]; then
  echo "You should provide db user name as second argument"
  exit 1
fi
if [ ! $3 ]; then
  echo "You should provide db password as third argument"
  exit 1
fi
if [ ! $4 ]; then
  echo "You should provide username as 4th argument"
  exit 1
else
  user=$4
fi
if [ ! $5 ]; then
  group="administrators"
else
  group = $5
fi

mysql -u$2 -p$3 -D$1<<SQL
    SET @group_id = (SELECT id FROM groups WHERE group_name = '$group');
    INSERT INTO group_members(username, group_id, group_name) VALUES('$user', @group_id, '$group');
SQL

if [ $? -eq 0 ]; then
  echo "Make $user belongs to $group"
else
  echo "Failed to Make $user belongs to $group"
  exit 255
fi