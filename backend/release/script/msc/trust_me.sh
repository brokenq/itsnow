#!/bin/sh

#
# Usage trust_me.sh host user password
#

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 1
fi

if [ ! $2 ]; then
  echo "You should provide user as second argument"
  exit 2
fi

if [ ! $3 ]; then
  echo "You should provide password as third argument"
  exit 3
fi

host=$1
user=$2
password=$3
file=`hostname`.pub

sshpass -p $password scp ~/.ssh/id_rsa.pub $user@$host:/root/.ssh/$file
sshpass -p $password ssh $user@$host cat /root/.ssh/$file >> /root/.ssh/authorized_keys
sshpass -p $password ssh $user@$host rm -f /root/.ssh/$file

ssh $user@$host ls /opt

if [ $? -gt 0 ]; then
  echo "Failed to setup trust relationship"
  exit 128
else
  echo "$host trust me(`hostname`)"
fi
