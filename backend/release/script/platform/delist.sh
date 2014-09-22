#!/bin/bash

# first stop mysql

service mysql stop
chkconfig mysql off
chkconfig --del mysql

# second stop redis
service redis stop
chkconfig redis off
chkconfig --del redis
