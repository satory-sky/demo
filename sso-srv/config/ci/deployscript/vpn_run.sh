#!/bin/bash

#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/deployscript/vpn_run.sh on
#sh /home/teamcity/sso/build/srv/dev/current/workcopy/config/ci/deployscript/vpn_run.sh off

VPN_COMMAND=$1

echo ">>ssh: run vpn from sudoer user"
ssh -o 'StrictHostKeyChecking no' -v -i /home/teamcity/.ssh/id_rsa colibri@127.0.0.1 'bash -s' "$VPN_COMMAND" << 'ENDSSH'

VPN_COMMAND=$1
echo "vpn command is $VPN_COMMAND"
if [ $VPN_COMMAND = "on" ]; then
    sudo pon fpc
    sleep 5
    sudo route add default dev ppp0
    sleep 5
    echo "VPN to FPC is ON"
elif [ $VPN_COMMAND = "off" ]; then
    sudo poff fpc
    sleep 5
    echo "VPN to FPC is OFF"
else
    echo "[CI] [ERROR] wrong vpn command"
    exit 4
fi

ENDSSH
echo "<<ssh: run vpn from sudoer user"