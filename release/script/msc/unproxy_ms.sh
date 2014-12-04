#!/bin/sh

#
# Usage: unproxy_ms.sh instance
#
#
#

if [ ! $1 ]; then
  echo "You should provide itsnow process instance name as first argument"
  exit 1
fi
instance=$1

conf=/etc/nginx/conf.d/$instance.conf
bak=/etc/nginx/conf.d/$instance.bak

/bin/mv -f $conf $bak

# 虽然一般来说移除某个nginx配置文件不会导致 nginx 配置出差
#  但也害怕其他外界干扰导致nginx配置错误，而后这里强制reload一下，整个系统就挂了
nginx -t 2>&1 | grep "syntax is ok"
if [ $? -eq 0 ]; then
  nginx -s reload
  echo "Removed the nginx conf file: $conf of $instance"
  echo "Unproxy $instance "
else
  echo "Can't reload nginx, nginx stay in bad state, please resolve it ASAP"
  /bin/mv -f $bak $conf
  exit 1
fi