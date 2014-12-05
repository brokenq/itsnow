#!/bin/sh

#
# Usage prepare_host.sh host
#

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 1
fi

target=$1

msc_home=$(cd `dirname $0`; cd ../../; pwd)

echo "MSC Home is $msc_home"

ssh_exec(){
  ssh root@$target $@
}

# 测试到目标主机的互信关系是否建立
ssh_exec "ls /opt"

if [ $? -gt 0 ]; then
  echo "Can't ssh $target by ssh, please setup authenticate relation"
  exit 128
fi

ssh_exec mkdir -p /opt/system/platform /opt/system/binaries /opt/system/config /opt/redis
echo "copy platform script to $target:/opt/system/platform"
scp $msc_home/script/platform/* root@$target:/opt/system/platform/
echo "copy binaries to $target:/opt/system/binaries"
scp /opt/system/binaries/* root@$target:/opt/system/binaries/
echo "copy config to $target:/opt/system/config"
scp /opt/system/config/* root@$target:/opt/system/config/
