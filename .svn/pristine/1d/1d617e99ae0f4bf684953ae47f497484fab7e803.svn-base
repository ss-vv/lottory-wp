#!/bin/sh 

cd /app/weibo-data
cp=`find ./lib -name "*.jar" | awk 'BEGIN{a="classes"}{a=a":"$0}END{print a}'`
JAVA_OPTS="-Xmx4096m -Xms1024m -XX:PermSize=512m -XX:MaxNewSize=1024m -XX:MaxPermSize=1024m -Xss2m -server"
JAVACMD="java $JAVA_OPTS -classpath conf:$cp core.WeiboDSLauncher"

if [ -z "$RUNFILE" ]; then
   RUNFILE="/dev/shm/weibo-data"
fi

checkRunning(){
    if [ -f "$RUNFILE" ]; then
        return 0;
    else
        return 1;
    fi
}

invoke_start(){
    if ( checkRunning );then
    echo "Process is already running"
    exit 0
    fi
    echo "Starting"
    echo '' > $RUNFILE;
        $JAVACMD 2>/data/logs/weibo-data-error.log  & 
    exit "$?" 
}

invoke_stop(){
    RET="1"
    if ( checkRunning );then
    rm -f $RUNFILE >/dev/null 2>&1
    echo "Stopping, please waiting..."
        RET="1"
    else
       echo "Process not running"
       exit 0
    fi
    exit $RET
}

show_help() {
  RET="$?"
  cat << EOF
Usage: run.sh task
Tasks:
    start          - start app
    stop           - stop app
EOF
  exit $RET
}

# ------------------------------------------------------------------------
if [ -z "$1" ];then
 show_help
fi

case "$1" in
  start)    
    invoke_start
    ;;
  stop)    
    invoke_stop
    ;;
esac