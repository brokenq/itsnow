#!/bin/sh

#
# Usage: check_process_status.sh address port name
#   return code = 0: running
#   return code = 1: stopped
#   return code = 2: abnormal(started, but http service not ready)
#   return code = 3: unknown
#   return code = 128: bad host address
#   return code = 255: bad arguments
# 基于主机状态已经OK的情况下，检测主机下的特定服务的状态
#

if [ ! $1 ]; then
  echo "You should provide host name/address as first argument"
  exit 128
fi
host=$1
if [ ! $2 ]; then
  echo "You should provide service http port as second argument"
  exit 128
fi
port=$2
if [ ! $3 ]; then
  echo "You should provide service name(without itnow_ prefix) as third argument"
  exit 128
fi
name=$3

curl "http://$host:$port/routes" 2>/dev/null > /dev/null

if [ $? -eq 0 ]; then
  exit 0 # http service ready , so status is ok
fi

dir=$(dirname $0)
$dir/check_host_status.sh $host
host_status=$?
if [ $host_status -eq 128 ]; then
  # bad address
  exit $host_status
fi
if [ $host_status -ne 0 ]; then
  # host shutdown, or abnormal
  echo "Host shutdown or not trusting me, unknown"
  exit 3
fi
ssh root@$host "/opt/itsnow/itsnow_$name/bin/itsnow-$name status"
if [ $? -eq 0 ]; then
  exit 2 # process is ok
else
  exit 1
fi