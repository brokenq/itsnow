#!/bin/sh

#
# Usage: proxy_ms.sh host instance
#
#  proxy for the instance at host
#

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 1
fi
host=$1

if [ ! $2 ]; then
  echo "You should provide itsnow process instance name as second argument"
  exit 2
fi
instance=$2

conf=/etc/nginx/conf.d/$instance.conf
bad=/etc/nginx/conf.d/$instance.bad

scp root@$host:/opt/itsnow/$instance/resources/nginx.conf $conf

nginx -t 2>&1 | grep "syntax is ok"

if [ $? -eq 0 ]; then
  nginx -s reload
  echo "Added a new nginx conf file: $conf"
  echo "Proxy for $instance at $host"
else
  mv $conf $bad
  echo "The nginx conf file of $instance is invalid, see: $bad"
  exit 1
fi