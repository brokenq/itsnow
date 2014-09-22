#!/bin/sh

# Configure a new itsnow system(msc/msu/msp) 
#   when the new system has been expanded, such as: msc has been extract to /opt/itsnow/msc-0.1.7
#    and the schema has been created(so the db user and password is provided)
# 
#  Usage  deploy.sh type instance varialbes.properites
#
#   1. interpolate config/now.properties (app.id, http.port,  mysql host/port/user/password, redis host/port/index)
#   2. create and interpolate db/migrate/environments/production.properites
#   3. change bin/*.sh db/bin/migrate mode
#   4. migrate the database
