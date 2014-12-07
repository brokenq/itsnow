#!/bin/bash

#Usage: ./bin/get_pid.sh target log-file-name path/to/itsnow

if [ ! $1 ]; then
    echo "You should provide instance id as first argument"
    exit 1
fi
instance=$1

if [ ! $2 ]; then
    echo "You should provide log file name as second argument"
    exit 1
fi
log_file_name=$2

if [ $3 ]; then
    folder=$3
else
    folder="/opt/itsnow"
fi

path=$folder/$instance/tmp/$log_file_name
row=$(grep "PID:" | $path)
pid=${row#*:}
exit pid