#!/bin/sh

#
# Usage provision.sh mysql_pwd
#

cd /opt/system/

# install prerequires
echo "Try to install prerequires by YUM"
yum groupinstall -y "Development Tools"
yum install -y wget lsof vim telnet

cd /opt/system/binaries

# install jdk if needs
java=`which java`
if [ "$java" == "" ]; then
  java="/usr/local/java/bin/java"
fi
if [ "$java" != "/bin/java" ]; then
  ln -s $java /bin/java
fi

# install redis
echo "Install redis..."
tar xvf redis-2.8.16.tar.gz
cd redis-2.8.16
make && make install
echo "vm.overcommit_memory=1" >> /etc/sysctl.conf
sysctl vm.overcommit_memory=1

echo "Redis installed"

echo "Configure redis..."
# configure redis
cd /opt/system/config
cp redis.conf /etc/
cp redis /etc/init.d/
chkconfig --add redis
chkconfig redis on
service redis start
echo "Redis started and auto-configured as service"

# install mysql

echo "Install mysql"
##  erase mariadb if exists
yum erase -y mariadb mariadb-libs
##  install mysql by rpm
cd /opt/system/binaries
rpm -ivh MySQL*.rpm
echo "Mysql installed"


echo "Configure mysql"
##  config mysql as service
chkconfig mysql on
## change default password
new_pwd=`cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 8 | head -n 1`
# 在第一次安装时候，会有这个 .mysql_secret 文件
if [ -f ~/.mysql_secret ]; then
  random=`cat ~/.mysql_secret | head -1 | awk -F:\  '{print $2}'`
  ##  start mysql service
  service mysql start
  mysqladmin -uroot -p$random password $new_pwd
  rm -f ~/.mysql_secret
else
  # 需要重置
  mysqld_safe --skip-grant-tables &
  # Wait for the mysqld_safe process to start
  while ! [[ "$mysqld_process_pid" =~ ^[0-9]+$ ]]; do
    mysqld_process_pid=$(ps aux | grep -v grep | grep -v mysqld_safe| grep mysqld | awk '{print $2}')
    sleep 1
  done

  mysql -uroot -Dmysql -e "update user set password=password('$new_pwd') where user='root'"
  service mysql restart
fi
echo $new_pwd > /root/.mysql_pwd

echo "MySQL password reset and store in /root/.mysql_pwd"

echo "Temp solution for mysql master/slave replication"
script_dir=$(cd `dirname $0` && pwd )
$script_dir/create_db.sh itsnow_msc itsnow secret
mysql -uitsnow -psecret -Ditsnow_msc < $script_dir/itsnow_msc.sql
if [ $? -eq 0 ]; then
  echo "Replication of itsnow_msc created!"
else
  echo "Failed to create itsnow_msc replication"
  exit 1
fi