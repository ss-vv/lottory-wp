#!/bin/sh 

cd /app/mc_for_bonus
cp=`find ./lib -name "*.jar" | awk 'BEGIN{a="classes"}{a=a":"$0}END{print a}'`
JAVA_OPTS="-Xmx4096m -Xms1024m -XX:PermSize=512m -XX:MaxNewSize=1024m -XX:MaxPermSize=1024m -Xss2m -server"
JAVACMD="java $JAVA_OPTS -classpath conf:$cp com.xhcms.lottery.mc.jc.handler.JCBonusCheckerForOldPlatform"

show_help() {
  RET="$?"
  cat << EOF
Usage: check_bonus_for_old_platform.sh <end time>
EOF
  exit $RET
}

# ------------------------------------------------------------------------
if [ -z "$1" ];then
 show_help
fi

#$JAVACMD $1
java $JAVA_OPTS -classpath conf:$cp com.xhcms.lottery.mc.jc.handler.JCBonusCheckerForOldPlatform "2012-08-16 11:20:00"
