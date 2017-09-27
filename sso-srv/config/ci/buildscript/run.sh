#!/bin/sh

#sh config/ci/buildscript/run.sh %build.number% dev
#sh config/ci/buildscript/run.sh %build.number% demo
#sh config/ci/buildscript/run.sh %build.number% prod

echo "landscape_path>>"
DEST=`date +%Y%m%d_%H%M%S_`$1
GENERAL_DIR="/home/teamcity/sso/build/srv/$2"
CURRENT_DIR=$GENERAL_DIR/$DEST
CURRENT_LINK=$GENERAL_DIR/current
echo "CURRENT_DIR dir is: $CURRENT_DIR"
echo "landscape_path<<"

echo "prepare_landscape>>"
mkdir -p $CURRENT_DIR/workcopy
mkdir -p $CURRENT_DIR/apps
echo -e "created: \n$CURRENT_DIR/workcopy \n$CURRENT_DIR/apps"
echo "prepare_landscape<<"

echo "prepare_data>>"
rsync -av --progress --exclude '***/.gradle' --exclude='***/build/***' --exclude 'lib*' --exclude '*.iml' --exclude '*.gitignore' ./* $CURRENT_DIR/workcopy
echo "copy artefacts"
rsync -av --progress --exclude=*.VOB --exclude=*.avi ./sso-flow/build/libs/sso-flow.war $CURRENT_DIR/apps/
echo "prepare_data<<"

echo "prepare_current_link>>"
rm -r -f $CURRENT_LINK
ln -s $CURRENT_DIR $CURRENT_LINK
echo "prepare_current_link<<"

echo "remove_osolete_versions>>"
cd $GENERAL_DIR && ls -1t $GENERAL_DIR | tail -n +21 | xargs rm -r -f
echo "remove_osolete_versions<<"
