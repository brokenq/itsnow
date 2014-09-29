#!/bin/sh

#
# Replicate a mysql slave for itsnow_msc
#
#  Usage replicate slave_address slave_index
#
#  Step 1 [master] lock the master itsnow_msc (lock.sh)
#  Step 2 [master] create my.conf for the slave
#  Step 3 [master] dump the itsnow_msc with master info to itsnow_msc.sql
#  Step 4 [master] scp the itsnow_msc.sql to slave
#  Step 5 [slave]  create schema itsnow_msc
#  Step 6 [slave]  import the itsnow_msc.sql to itsnow_msc
#  Step 7 [slave]  start slave
#  Step 8 [slave]  show slave status;
#  Step 9 [master] unlock tables;(lock.sh)

if [ ! $1 ]; then
  echo "You must specify the slave address as first argument"
  exit 1
fi
if [ ! $2 ]; then
  echo "You must specify the slave index as second argument"
  exit 2
fi

slave=$1
index=$2
master_password=`cat ~/.mysql_pwd`
slave_password=`ssh root@$slave "cat /root/.mysql_pwd"`

function slave_mysql_exec(){
  ssh root@$slave "cd /var/lib/mysql && mysql -uroot -p$slave_password --auto-vertical-output -e \"$1\" "
}

FIFO="/tmp/mysql-pipe.$$"
mkfifo ${FIFO} || exit $?
RC=0

# Tie FD3 to the FIFO (only for writing), then start MySQL in the u
# background with its input from the FIFO:
exec 3<>${FIFO}

mysql -uroot -p$master_password <${FIFO} &
MYSQL=$!
trap "rm -f ${FIFO};kill -1 ${MYSQL} 2>&-" 0

# Now lock the table...
echo "Step 1 lock master tables"
echo "FLUSH TABLES WITH READ LOCK;" >&3

cd /var/lib/mysql/
echo "Step 2 create my.cnf for $slave"
sed "s/server-id=[0-9]\+/server-id=$index/g" /opt/system/config/my-slave.cnf  > $slave.cnf
scp $slave.cnf root@$slave:/usr/my.cnf
ssh root@$slave "service mysql restart"
echo "Step 3 dump master itsnow_msc"
mysqldump -uroot -p$master_password itsnow_msc > itsnow_msc.sql
echo "Step 4 copy itsnow_msc.sql to $slave"
scp itsnow_msc.sql root@$slave:/var/lib/mysql/
echo "Step 5,6,7 $slave import itsnow_msc and start slave"
slave_mysql_exec "CREATE DATABASE itsnow_msc DEFAULT CHARACTER SET UTF8; USE itsnow_msc; SOURCE itsnow_msc.sql;"
eval $(mysql -uroot -p$master_password  -e "show master status" | head -4 | tail -1 | awk '{printf("log_file=%s\nlog_pos=%s\n",$1,$2);}')
slave_mysql_exec "CHANGE MASTER TO master_host = 'srv1', master_port = 3306, master_user='repl', master_password='repl-of-itsnow',master_log_file='$log_file',master_log_pos=$log_pos ;START SLAVE;"
echo "Step 8 check slave status"
io_status=$(slave_mysql_exec "SHOW SLAVE STATUS" | head -12 | tail -1 | awk -F: '{print $2}')
sql_status=$(slave_mysql_exec "SHOW SLAVE STATUS" | head -13 | tail -1 | awk -F: '{print $2}')
if [[ "$io_status" =~ "Yes" ]]; then
  echo "IO thread ok"
else
  echo "IO failed when configure $slave with mysql index = $index"
  RC=127
fi
if [[ "$sql_status" =~ "Yes" ]]; then
  echo "SQL thread ok"
else
  echo "SQL failed when configure $slave with mysql index = $index"
  RC=128
fi

echo "Step 9 unlock master tables"
echo "UNLOCK TABLES;" >&3
exec 3>&-

# You probably wish to sleep for a bit, or wait on ${MYSQL} before you exit
sleep 5

exit $RC