#!/bin/bash

# first stop mysql
echo "Stop mysql"
service mysql stop
chkconfig mysql off
chkconfig --del mysql
echo "Mysql stopped"

echo "Uninstall mysql"
rpm -qa | grep MySQL | xargs rpm -e
rm -f ~/.mysql_*
rm -rf /var/lib/mysql
echo "Mysql uninstalled"

echo "Stop redis"
# second stop redis
service redis stop
chkconfig redis off
chkconfig --del redis
echo "Redis stopped"

echo "Uninstall redis"
rm -f /usr/local/bin/redis-*
rm -f /etc/redis.conf
rm -f /etc/init.d/redis
echo "Redis stopped"
