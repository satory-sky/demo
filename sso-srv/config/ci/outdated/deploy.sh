#!/bin/sh

echo "landscape_path>>"
GENERAL_BUILD_DIR="/home/teamcity/pritle/build"
CURRENT_BUILD_LINK=$GENERAL_BUILD_DIR/current
CURRENT_BUILD_DIR=$(readlink $CURRENT_BUILD_LINK)
DEST=$(basename $CURRENT_BUILD_DIR)
GENERAL_DEPLOY_DIR="/home/ubuntu/pritle/deploy"
echo "landscape_path<<"

echo "set_landscape>>"
cd $CURRENT_BUILD_LINK/workcopy/deploy
chmod +x set_landscape.sh && ./set_landscape.sh $GENERAL_DEPLOY_DIR $DEST
IS_COPY_EXISTS=$?
echo "set_landscape<<"

echo "deploy>> : $IS_COPY_EXISTS"
#if current build has not been deployed yet
if [ 0 -eq $IS_COPY_EXISTS ]; then
    echo "pack_and_copy>>"
    chmod +x pack_and_copy.sh && ./pack_and_copy.sh $GENERAL_DEPLOY_DIR $DEST $CURRENT_BUILD_LINK
    echo "pack_and_copy<<"

    echo "unpack>>"
    chmod +x unpack.sh && ./unpack.sh $GENERAL_DEPLOY_DIR $DEST
    echo "unpack<<"
fi
echo "deploy<<"