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

host=$1
user=$2
export SSHPASS=$3
file=`hostname`.pub
test=ls / > /dev/null

function echo_and_exec(){
  cmd="$@"
  echo $cmd
  ${cmd}
}

function simple_ssh_exec(){
  echo_and_exec ssh -o StrictHostKeyChecking=no -o PasswordAuthentication=no $user@$host  $@
}

function scp_exec(){
  echo_and_exec sshpass -p $SSHPASS scp -o StrictHostKeyChecking=no $1 $user@$host:$2
  code=$?
  if [ $code -gt 0 ]; then
    echo "Can't exec \"$cmd\""
    exit $code
  fi
}
function ssh_exec(){
  echo_and_exec sshpass -p $SSHPASS ssh -o StrictHostKeyChecking=no $user@$host $@
  code=$?
  if [ $code -gt 0 ]; then
    echo "Can't exec \"$cmd\""
    exit $code
  fi
}

# Test I'm trusted or not
simple_ssh_exec $test
if [ $? -eq 0 ]; then
  echo "$host has trusted me(`hostname`)"
  exit 0
else
  echo "Making $host trust `hostname`..."
fi

# Test sshpass can work or not
ssh_exec $test
# Perform real works
scp_exec ~/.ssh/id_rsa.pub /root/.ssh/$file
ssh_exec "/bin/cp -f /root/.ssh/authorized_keys /root/.ssh/authorized_keys.bak"
ssh_exec "cat /root/.ssh/$file >> /root/.ssh/authorized_keys"
ssh_exec "rm -f /root/.ssh/$file"

# verify trust relationship
simple_ssh_exec $test
code=$?
if [ $code -gt 0 ]; then
  echo "Failed to setup trust relationship"
  exit $code
else
  echo "$host have trust me(`hostname`)"
fi
