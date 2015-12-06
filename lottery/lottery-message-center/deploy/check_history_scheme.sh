#!/bin/sh 

cd /app/mc_for_history
cp=`find ./lib -name "*.jar" | awk 'BEGIN{a="classes"}{a=a":"$0}END{print a}'`
JAVA_OPTS="-Xmx4096m -Xms1024m -XX:PermSize=512m -XX:MaxNewSize=1024m -XX:MaxPermSize=1024m -Xss2m -server"
JAVACMD="java $JAVA_OPTS -classpath conf:$cp com.xhcms.lottery.mc.jc.handler.JCHistoryTicketChecker"

show_help() {
  RET="$?"
  cat << EOF
Usage: check_history_scheme.sh <one_scheme_id>
EOF
  exit $RET
}

# ------------------------------------------------------------------------
if [ -z "$1" ];then
 show_help
fi

$JAVACMD $1
