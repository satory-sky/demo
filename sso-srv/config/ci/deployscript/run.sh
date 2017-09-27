#!/bin/sh

#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/deployscript/run.sh id_rsa 127.0.0.1 dev
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/deployscript/run.sh id_rsa_demo 10.77.163.213 demo
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/deployscript/run.sh id_rsa_prod 10.77.163.212 prod

# liquibase (Working directory:) /home/teamcity/sso/build/srv/dev/current/workcopy

echo "landscape_path>>"
GENERAL_BUILD_DIR="/home/teamcity/sso/build/srv/$3"
ID_RSA_KEY_NAME=$1
REMOTE_SERVER=$2
CURRENT_BUILD_LINK=$GENERAL_BUILD_DIR/current
CURRENT_BUILD_DIR=$(readlink $CURRENT_BUILD_LINK)
DEST=$(basename $CURRENT_BUILD_DIR)
GENERAL_DEPLOY_DIR="/home/colibri/sso/deploy/srv"
echo "landscape_path<<"

echo "set_landscape>>"
cd $CURRENT_BUILD_LINK/workcopy/config/ci/deployscript
chmod +x set_landscape.sh && ./set_landscape.sh $GENERAL_DEPLOY_DIR $DEST $ID_RSA_KEY_NAME $REMOTE_SERVER
IS_COPY_EXISTS=$?
echo "set_landscape<<"

echo "deploy>> : $IS_COPY_EXISTS"
#if current build has not been deployed yet
if [ 0 -eq $IS_COPY_EXISTS ]; then
    echo "pack_and_copy>>"
    chmod +x pack_and_copy.sh && ./pack_and_copy.sh $GENERAL_DEPLOY_DIR $DEST $CURRENT_BUILD_LINK $ID_RSA_KEY_NAME $REMOTE_SERVER
    echo "pack_and_copy<<"

    echo "unpack>>"
    chmod +x unpack.sh && ./unpack.sh $GENERAL_DEPLOY_DIR $DEST $ID_RSA_KEY_NAME $REMOTE_SERVER
    echo "unpack<<"
fi
echo "deploy<<"