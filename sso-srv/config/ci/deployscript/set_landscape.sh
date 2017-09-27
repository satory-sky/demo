#!/bin/bash

GENERAL_DEPLOY_DIR=$1
DEST=$2
ID_RSA_KEY_NAME=$3
REMOTE_SERVER=$4

echo "ssh>>"
ssh -o 'StrictHostKeyChecking no' -v -i /home/teamcity/.ssh/$ID_RSA_KEY_NAME colibri@$REMOTE_SERVER 'bash -s' "$GENERAL_DEPLOY_DIR" "$DEST" << 'ENDSSH'

GENERAL_DEPLOY_DIR=$1
DEST=$2
CURRENT_DEPLOY_DIR=$GENERAL_DEPLOY_DIR/$DEST
CURRENT_DEPLOY_LINK=$GENERAL_DEPLOY_DIR/current
# check if deploy folder exists
# if not - create it
if [ ! -d $GENERAL_DEPLOY_DIR/ ]; then
    mkdir -p $GENERAL_DEPLOY_DIR/
    echo -e "created: \n$GENERAL_DEPLOY_DIR/"
# if yes - check if the same build has been already deployed (to avoid deploy of the dublicate)
elif [ -d $CURRENT_DEPLOY_DIR ]; then
    echo "[CI] [ERROR] current build has been deployed already"
    exit 9
fi

ENDSSH
IS_COPY_EXISTS=$?
echo "ssh<< : $IS_COPY_EXISTS"

exit $IS_COPY_EXISTS