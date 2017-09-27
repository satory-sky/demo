#!/bin/sh

#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/backupscript/run.sh deploy dev vbn7nme3 127.0.0.1
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/backupscript/run.sh deploy demo dxh5duk3 10.77.163.213
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/backupscript/run.sh deploy prod dxh5duk3 10.77.163.248

#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/backupscript/run.sh %build.number% dev vbn7nme3 127.0.0.1
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/backupscript/run.sh %build.number% demo dxh5duk3 10.77.163.213
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/backupscript/run.sh %build.number% prod dxh5duk3 10.77.163.248

echo "landscape_path>>"
DEST=`date +%Y%m%d_%H%M%S_`$1
EVENT=auto
if [ $1 = "deploy" ]; then
    DEST=`date +%Y%m%d_%H%M%S`
    EVENT=deploy
fi
GENERAL_DIR="/home/teamcity/sso/backup/db/$EVENT/$2"
CURRENT_DIR=$GENERAL_DIR/$DEST
CURRENT_LINK=$GENERAL_DIR/current
REMOTE_DIR="/home/colibri/sso/backup/db"
echo "CURRENT_DIR dir is: $CURRENT_DIR"
echo "landscape_path<<"

echo "prepare_landscape>>"
mkdir -p $CURRENT_DIR/
echo -e "created: \n$CURRENT_DIR"
echo "prepare_landscape<<"

echo "create_dumps>>"
export PGPASSWORD=$3
pg_dump -h $4 -U postgres --clean sso-flow > $CURRENT_DIR/ter_caine.sql
pg_dump -h $4 -U postgres -Fc --clean sso-flow > $CURRENT_DIR/ter_caine.dump
echo "create_dumps<<"

echo "prepare_current_link>>"
rm -r -f $CURRENT_LINK
ln -s $CURRENT_DIR $CURRENT_LINK
echo "prepare_current_link<<"

echo "remove_osolete_versions>>"
cd $GENERAL_DIR && ls -1t $GENERAL_DIR | tail -n +21 | xargs rm -r -f
echo "remove_osolete_versions<<"