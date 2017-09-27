#!/bin/sh

#http://blog.maestropublishing.com/2013/04/23/tomcat7-service-startstop-script/

TOMCAT_HOME=/home/tomcat/apache-tomcat-7.0.54
SHUTDOWN_WAIT=20

tomcat_pid() {
  echo `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
}

start() {
  pid=$(tomcat_pid)
  if [ -n "$pid" ]
  then
    echo "Tomcat is already running (pid: $pid)"
  else
    # Start tomcat
    echo "Starting tomcat"
    ulimit -n 100000
    umask 007
    # /bin/su -p -s /bin/sh root $TOMCAT_HOME/bin/startup.sh
    export JPDA_ADDRESS=8000
    export JPDA_TRANSPORT=dt_socket
    $TOMCAT_HOME/bin/catalina.sh jpda start
  fi


  return 0
}

stop() {
  pid=$(tomcat_pid)
  if [ -n "$pid" ]
  then
    echo "Stoping Tomcat"
    # /bin/su -p -s /bin/sh root $TOMCAT_HOME/bin/shutdown.sh
    $TOMCAT_HOME/bin/shutdown.sh

    COUNT=0;
    until [ `ps -p $pid | grep -c $pid` = '0' ] || [ $COUNT -gt $SHUTDOWN_WAIT ]
    do
      echo -n -e "\nwaiting for processes to exit\n";
      sleep 1
      COUNT=$((COUNT + 1))
    done

    if [ $COUNT -gt $SHUTDOWN_WAIT ]; then
      echo -n -e "\nkilling processes which didn't stop after $SHUTDOWN_WAIT seconds"
      kill -9 $pid
    fi
  else
    echo "Tomcat is not running"
  fi

  return 0
}

case $1 in
start)
  start
;;
stop)
  stop
;;
restart)
  stop
  start
;;
status)
  pid=$(tomcat_pid)
  if [ -n "$pid" ]
  then
    echo "Tomcat is running with pid: $pid"
  else
    echo "Tomcat is not running"
  fi
;;
esac
exit 0