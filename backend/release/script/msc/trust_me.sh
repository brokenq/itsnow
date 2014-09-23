#!/bin/sh

#
# Usage trust_me.sh host user password
#

if [ ! $1 ]; then
  echo "You should provide target host name/address as first argument"
  exit 1
fi

if [ ! $2 ]; then
  echo "You should provide user as second argument"
  exit 2
fi

if [ ! $3 ]; then
  echo "You should provide password as third argument"
  exit 3
fi

echo "Process path is: $PATH"

host=$1
user=$2
password=$3
file=`hostname`.pub
ssh_pass=`which sshpass`

function echo_and_exec(){
  cmd="$@"
  echo $cmd
  ${cmd}
}

function simple_ssh_exec(){
  echo_and_exec ssh -o StrictHostKeyChecking=no -o LogLevel=quiet -o PasswordAuthentication=no $user@$host  $@
}

function scp_exec(){
  echo_and_exec $ssh_pass -p $password scp -o StrictHostKeyChecking=no -o LogLevel=quiet $1 $user@$host:$2
  code=$?
  if [ $code -gt 0 ]; then
    echo "Can't exec \"$cmd\""
    exit $code
  fi
}
function ssh_exec(){
  echo_and_exec $ssh_pass -p $password ssh -o StrictHostKeyChecking=no -o LogLevel=quiet $user@$host $@
  code=$?
  if [ $code -gt 0 ]; then
    echo "Can't exec \"$cmd\""
    exit $code
  fi
}

# Try to connect
simple_ssh_exec ls /opt
if [ $? -eq 0 ]; then
  echo "$host has trusted me(`hostname`)"
  exit 0
else
  echo "Making $host trust `hostname`..."
fi

scp_exec ~/.ssh/id_rsa.pub /root/.ssh/$file
ssh_exec "/bin/cp -f /root/.ssh/authorized_keys /root/.ssh/authorized_keys.bak"
ssh_exec "cat /root/.ssh/$file >> /root/.ssh/authorized_keys"

#ssh_exec "rm -f /root/.ssh/$file"

simple_ssh_exec ls /opt
code=$?
# verify trust relationship

if [ $code -gt 0 ]; then
  echo "Failed to setup trust relationship"
  exit $code
else
  echo "$host trust me(`hostname`)"
fi
