#!/bin/bash

GENERAL_DEPLOY_DIR=$1
DEST=$2
CURRENT_BUILD_LINK=$3

echo "pack_archive>>"
cd $CURRENT_BUILD_LINK/..
zip -r $CURRENT_BUILD_LINK/archive-full $DEST
echo "pack_archive<<"

# copy process from teamcity user:
#su teamcity
#cd /home/teamcity/TeamCity/buildAgent/work/f10510db137401e5
echo "copy>>"
#rsync -avz --progress -e "ssh -v -i /home/teamcity/.ssh/JavaKey.pem -p 22" ./pritle-coral/build/libs/pritle-coral.war ubuntu@54.72.224.105:/home/ubuntu/pritle/
scp -v -i /home/teamcity/.ssh/JavaKey.pem $CURRENT_BUILD_LINK/archive-full.zip ubuntu@54.72.224.105:$GENERAL_DEPLOY_DIR/
echo "copy<<"


