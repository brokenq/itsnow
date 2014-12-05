#!/bin/sh

#
# Usage check_host_user.sh host user password
#
#   return code = 0: correct
#   return code = 1: user / password incorrect
#   return code = 128: bad address
#   return code = 255: bad arguments
#

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 255
fi

if [ ! $2 ]; then
  echo "You should provide user as second argument"
  exit 255
fi

if [ ! $3 ]; then
  echo "You should provide password as third argument"
  exit 255
fi
host=$1
user=$2
password=$3

ip=$(host $host | awk '{print $4}')
msc_ip=$(hostname -i)
if [ "$ip" == "$msc_ip" ]; then
  echo "You should provide a host beside msc host"
  exit 128
fi

sshpass -p $password ssh -o StrictHostKeyChecking=no $user@$host "ls / > /dev/null"
if [ $? -eq 0 ]; then
  exit 0
else
  echo 1
fi
