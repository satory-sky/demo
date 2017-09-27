#!/bin/bash

#sh /home/teamcity/sso/build/srv/current/workcopy/config/ci/deployscript/vpn_switch.sh on
#sh /home/teamcity/sso/build/srv/current/workcopy/config/ci/deployscript/vpn_switch.sh off

function vpn_on {
    pon fpc
    sleep 5
    route add default dev ppp0
    sleep 5
    echo "VPN to FPC is ON"
}

function vpn_off {
    poff fpc
    sleep 5
    echo "VPN to FPC is OFF"
}

case "$1" in
on)
    vpn_on
    ;;
off)
    vpn_off
    ;;
*)
    echo "usage: $0 (on| off)"
esac
