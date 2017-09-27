#!/bin/sh

echo "deploy>>"

echo "landscape_path>>"
DEST=`date +%Y%m%d_%H%M%S_`$1
HOME_BUILD_DIR="/home/ubuntu/pritle/build/"
CURRENT_BUILD_DIR=$HOME_BUILD_DIR$DEST
echo "CURRENT_BUILD_DIR dir is: $CURRENT_BUILD_DIR"
echo "landscape_path<<"


echo "prepare_landscape>>"
echo "ssh>>"
ssh -v -i /home/teamcity/.ssh/JavaKey.pem ubuntu@54.72.224.105 'bash -s' "$CURRENT_BUILD_DIR" << 'ENDSSH'

mkdir -p $1
echo -e "created: \n$1"

#mkdir -p $1/workcopy
#mkdir -p $1/apps
#mkdir -p $1/database
#echo -e "created: \n$1/workcopy \n$1/apps \n$1/database"

ENDSSH
echo "ssh<<"
echo "prepare_landscape<<"


echo "prepare_archive>>"
rsync -av --progress --exclude='build*' --exclude 'lib*' --exclude '*.iml' --exclude '*.gradle' --exclude '*.gitignore' ./* ./workcopy/
rsync -av --progress --exclude='build*' --exclude 'lib*' --exclude '*.iml' --exclude '*.gradle' --exclude '*.gitignore' ./pritle-db ./database/

rsync -av --progress --exclude=*.VOB --exclude=*.avi ./pritle-coral/build/libs/pritle-coral.war ./apps/

zip -r archive-full ./workcopy
zip -r archive-full ./apps
zip -r archive-full ./database

rm -r -f workcopy
rm -r -f apps
rm -r -f database
echo "prepare_archive<<"


#su teamcity
#cd /home/teamcity/TeamCity/buildAgent/work/f10510db137401e5
echo "copy>>"
#scp -v -i /home/teamcity/.ssh/JavaKey.pem ./pritle-coral/build/libs/pritle-coral.war ubuntu@54.72.224.105:${CURRENT_BUILD_DIR}
#rsync -avz --progress -e "ssh -v -i /home/teamcity/.ssh/JavaKey.pem -p 22" ./pritle-coral/build/libs/pritle-coral.war ubuntu@54.72.224.105:/home/ubuntu/pritle/

scp -v -i /home/teamcity/.ssh/JavaKey.pem ./archive-full.zip ubuntu@54.72.224.105:${CURRENT_BUILD_DIR}
rm ./archive-full.zip
sudo -u tomcat echo "...cleaned"
echo "copy<<"


echo "ssh>>"
ssh -v -i /home/teamcity/.ssh/JavaKey.pem ubuntu@54.72.224.105 'bash -s' "$CURRENT_BUILD_DIR" << 'ENDSSH'

unzip $1/archive-full.zip -d $1
rm -r -f $1/archive-full.zip

sudo -u tomcat /home/tomcat/apache-tomcat-7.0.54/bin/shutdown.sh
sudo -u tomcat echo "Tomcat shutted down."

sudo -u tomcat cp $1/pritle-coral.war /home/tomcat/apache-tomcat-7.0.54/webapps
sudo -u tomcat echo "...copied"

sudo -u tomcat rm -r -f /home/tomcat/apache-tomcat-7.0.54/webapps/pritle-coral
sudo -u tomcat echo "...cleaned"

sudo -u tomcat /home/tomcat/apache-tomcat-7.0.54/bin/startup.sh
ENDSSH
echo "ssh<<"

echo "deploy<<"
