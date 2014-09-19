#!/bin/sh

#
# Usage delist_host.sh host
#

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 1
fi

target=$1

ssh_exec(){
  ssh root@$target $@
}

# 测试到目标主机的互信关系是否建立
ssh_exec "ls /opt"

if [ $? -gt 0 ]; then
  echo "Can't ssh $target by ssh, please setup authenticate relation"
  exit 128
fi

ssh_exec rm -rf /opt/system/
