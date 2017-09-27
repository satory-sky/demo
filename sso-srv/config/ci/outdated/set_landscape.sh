#!/bin/bash

echo "ssh>>"
#su teamcity
ssh -v -i /home/teamcity/.ssh/JavaKey.pem ubuntu@54.72.224.105 'bash -s' "$1" "$2" << 'ENDSSH'

CURRENT_DEPLOY_DIR=$1/$2
CURRENT_DEPLOY_LINK=$1/current
# check if deploy folder exists
# if not - create it
if [ ! -d $1/ ]; then
    mkdir -p $1/
    echo -e "created: \n$1/"
# if yes - check if the same build has been already deployed (to avoid deploy of the dublicate)
elif [ -d $CURRENT_DEPLOY_DIR ]; then
    echo "[CI] [ERROR] current build has been deployed already"
    exit 9
fi

ENDSSH
IS_COPY_EXISTS=$?
echo "ssh<< : $IS_COPY_EXISTS"

exit $IS_COPY_EXISTS