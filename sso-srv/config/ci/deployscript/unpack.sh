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


echo "unzip_and_clean>>"
if [ ! -d $CURRENT_DEPLOY_DIR ]; then
    unzip $GENERAL_DEPLOY_DIR/archive-full.zip -d $GENERAL_DEPLOY_DIR/
    rm -r -f $GENERAL_DEPLOY_DIR/archive-full.zip
elif [ -f $GENERAL_DEPLOY_DIR/archive-full.zip ]; then
    rm -r -f $GENERAL_DEPLOY_DIR/archive-full.zip
fi
echo "unzip_and_clean<<"


echo "prepare_current_link>>"
rm -r -f $CURRENT_DEPLOY_LINK
ln -s $CURRENT_DEPLOY_DIR $CURRENT_DEPLOY_LINK
echo "prepare_current_link<<"


# global restart and deploy for all affected applications:
TOMCAT_USER=tomcat7
TOMCAT_DIR=/var/lib/$TOMCAT_USER
if [ ! -d $TOMCAT_DIR/ ]; then
    TOMCAT_USER=tomcat
fi
WEBAPPS_DIR=/var/lib/$TOMCAT_USER/webapps
echo "[CI] current tomcat webapps directory is: $WEBAPPS_DIR"

export TOMCAT_PID=`ps -ef | grep $TOMCAT_USER | awk 'NR==1{print $2}' | cut -d' ' -f1`
echo "tomcat pid: $TOMCAT_PID"
sudo kill -9 $TOMCAT_PID
echo "[CI] shutdown $TOMCAT_USER"

sudo rm -r -f $WEBAPPS_DIR/sso-flow.war
sudo rm -r -f $WEBAPPS_DIR/sso-flow
sudo echo "[CI] ...cleaned"

sudo cp -r $CURRENT_DEPLOY_DIR/apps/sso-flow.war $WEBAPPS_DIR
sudo echo "[CI] ...copied"

sudo service $TOMCAT_USER restart
echo "[CI] restart $TOMCAT_USER"

echo "remove_osolete_versions>>"
cd $GENERAL_DEPLOY_DIR && ls -1t $GENERAL_DEPLOY_DIR | tail -n +21 | xargs rm -r -f
echo "remove_osolete_versions<<"

ENDSSH
echo "ssh<<"