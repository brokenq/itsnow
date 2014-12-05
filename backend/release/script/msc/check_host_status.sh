#!/bin/sh

#
# Usage: check_host_status.sh address
#   return code = 0: normal
#   return code = 1: shutdown
#   return code = 2: abnormal(not trusted)
#   return code = 128: bad address
#   return code = 255: bad arguments
# 目标主机可能是域名，但itsnow域下不存在的域名又会被解析到 srv1 自身
#   所以需要先做域名/地址解析；如果地址为本地，则返回错误参数

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 255
fi

if [[ "$1" =~ [0-9]+\.[0-9]+\.[0-9]+\.[0-9]+ ]]; then
  ip=$1
else
  ip=$(host $1 | awk '{print $4}')
fi
msc_ip=$(hostname -i)
if [ "$ip" == "$msc_ip" ]; then
  echo "You should provide a host beside msc host"
  exit 128
fi

ping -c 2 -i 0.2 $ip > /dev/null

if [ $? -eq 0 ]; then
  ssh -o StrictHostKeyChecking=no -o PasswordAuthentication=no root@$ip "ls / > /dev/null"
  if [ $? -eq 0 ]; then
    exit 0
  else
    exit 2
  fi
else
  exit 1 # host shutdown
fi


