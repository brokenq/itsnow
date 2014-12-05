#!/bin/bash

#Usage: ./bin/check_stop.sh process-port max-seconds

if [ ! $1 ]; then
    echo "You should provide process port as first argument"
    exit 1
fi
port=$1
echo "Checking process port: $port"

if [ ! $2 ]; then
    max=60
else
    max=$2
fi

for((i=0;i<$max;i++)) do
    sleep 1
    pid=`lsof -i :$port|grep -v "PID" | awk '{print $2}'`
    if [ "$pid" == "" ]; then
        echo "Process has been stopped"
        exit 0
    else
        printf "."
    fi
done

echo "Process is not stopped until $max seconds"
exit 1
