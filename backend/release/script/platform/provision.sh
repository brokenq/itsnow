#!/bin/sh

#
# Usage provision.sh mysql_pwd
#

cd /opt/system/

# install prerequires
yum groupinstall -y "Development Tools"
yum install -y wget lsof vim telnet

cd /opt/system/binaries

# install jdk if needs

# install redis
tar xvf redis-2.8.16.tar.gz
cd redis-2.8.16
make && make install
echo "vm.overcommit_memory=1" >> /etc/sysctl.conf
sysctl vm.overcommit_memory=1

# configure redis
cd /opt/system/config
cp redis.conf /etc/
cp redis /etc/init.d/
chkconfig --add redis
chkconfig redis on
service redis start

# install mysql

##  erase mariadb if exists
yum erase -y mariadb mariadb-libs
##  install mysql by rpm
cd /opt/system/binaries
rpm -ivh MySQL*.rpm

##  config mysql as service
chkconfig mysql on
##  start mysql service
service mysql start
## change default password
random=`cat ~/.mysql_secret | head -1 | awk -F:\  '{print $2}'`
new_pwd=`cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 8 | head -n 1`
mysqladmin -uroot -p$random password $new_pwd
echo $new_pwd > /root/.mysql_pwd

