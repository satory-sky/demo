#!/bin/sh

echo "build>>"

echo "landscape_path>>"
DEST=`date +%Y%m%d_%H%M%S_`$1
GENERAL_BUILD_DIR="/home/teamcity/sso/build"
CURRENT_BUILD_DIR=$GENERAL_BUILD_DIR/$DEST
CURRENT_BUILD_LINK=$GENERAL_BUILD_DIR/current
echo "CURRENT_BUILD_DIR dir is: $CURRENT_BUILD_DIR"
echo "landscape_path<<"


echo "prepare_landscape>>"
mkdir -p $CURRENT_BUILD_DIR/workcopy
mkdir -p $CURRENT_BUILD_DIR/apps
echo -e "created: \n$CURRENT_BUILD_DIR/workcopy \n$CURRENT_BUILD_DIR/apps"
echo "prepare_landscape<<"


echo "prepare_data>>"
rsync -av --progress --exclude='build*' --exclude 'lib*' --exclude '*.iml' --exclude '*.gradle' --exclude '*.gitignore' ./* $CURRENT_BUILD_DIR/workcopy
rsync -av --progress --exclude='build*' --exclude '*.iml' --exclude '*.gradle' --exclude '*.gitignore' ./pritle-db $CURRENT_BUILD_DIR/
echo "copy artefacts"
rsync -av --progress --exclude=*.VOB --exclude=*.avi ./pritle-coral/build/libs/pritle-coral.war $CURRENT_BUILD_DIR/apps/
rsync -av --progress --exclude=*.VOB --exclude=*.avi ./pritle-es/es-tetralog/build/libs/es-tetralog.war $CURRENT_BUILD_DIR/apps/
echo "prepare_data<<"


echo "prepare_current_link>>"
rm -r -f $CURRENT_BUILD_LINK
ln -s $CURRENT_BUILD_DIR $CURRENT_BUILD_LINK
echo "prepare_current_link<<"

echo "build<<"