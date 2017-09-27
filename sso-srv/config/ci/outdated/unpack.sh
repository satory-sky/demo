#!/bin/bash

echo "ssh>>"
ssh -v -i /home/teamcity/.ssh/JavaKey.pem ubuntu@54.72.224.105 'bash -s' "$1" "$2" << 'ENDSSH'

CURRENT_DEPLOY_DIR=$1/$2
CURRENT_DEPLOY_LINK=$1/current


echo "unzip_and_clean>>"
if [ ! -d $CURRENT_DEPLOY_DIR ]; then
    unzip $1/archive-full.zip -d $1/
    rm -r -f $1/archive-full.zip
elif [ -f $1/archive-full.zip ]; then
    rm -r -f $1/archive-full.zip
fi
echo "unzip_and_clean<<"


echo "db_migration>>"
cd $CURRENT_DEPLOY_DIR/pritle-db
chmod +x ./update.sh && ./update.sh

if [ 0 -ne $? ]; then
  echo "[CI] [ERROR] migrate databases"
  exit 1
fi
echo "db_migration<<"


echo "prepare_current_link>>"
rm -r -f $CURRENT_DEPLOY_LINK
ln -s $CURRENT_DEPLOY_DIR $CURRENT_DEPLOY_LINK
echo "prepare_current_link<<"


# global restart and deploy for all affected applications:

# all operations with tomcat from tomcat user only!
# stop tomcat
cd $CURRENT_DEPLOY_DIR/workcopy/deploy
sudo chown tomcat tomcat.sh && sudo -u tomcat chmod +x tomcat.sh && sudo -u tomcat ./tomcat.sh stop

sudo -u tomcat cp $CURRENT_DEPLOY_DIR/apps/pritle-coral.war /home/tomcat/apache-tomcat-7.0.54/webapps
sudo -u tomcat cp $CURRENT_DEPLOY_DIR/apps/es-tetralog.war /home/tomcat/apache-tomcat-7.0.54/webapps
sudo -u tomcat echo "...copied"
sudo -u tomcat rm -r -f /home/tomcat/apache-tomcat-7.0.54/webapps/pritle-coral
sudo -u tomcat rm -r -f /home/tomcat/apache-tomcat-7.0.54/webapps/es-tetralog
sudo -u tomcat echo "...cleaned"

#start tomcat
sudo -u tomcat ./tomcat.sh start
ENDSSH
echo "ssh<<"