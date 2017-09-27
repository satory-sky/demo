#!/bin/bash

GENERAL_DEPLOY_DIR=$1
DEST=$2
CURRENT_BUILD_LINK=$3
ID_RSA_KEY_NAME=$4
REMOTE_SERVER=$5

echo "pack_archive>>"
cd $CURRENT_BUILD_LINK/..
zip -r $CURRENT_BUILD_LINK/archive-full $DEST
echo "pack_archive<<"

echo "copy>>"
#rsync -avz --progress -e "ssh -v -i /home/teamcity/.ssh/JavaKey.pem -p 22" ./pritle-coral/build/libs/pritle-coral.war ubuntu@54.72.224.105:/home/ubuntu/pritle/
scp -v -i /home/teamcity/.ssh/$ID_RSA_KEY_NAME $CURRENT_BUILD_LINK/archive-full.zip colibri@$REMOTE_SERVER:$GENERAL_DEPLOY_DIR/
echo "copy<<"