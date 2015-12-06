#!/bin/sh 

cd /app/data-statistic
cp=`find ./lib -name "*.jar" | awk 'BEGIN{a="classes"}{a=a":"$0}END{print a}'`
JAVA_OPTS="-Xmx1024m -Xms512m -XX:PermSize=512m -XX:MaxNewSize=1024m -XX:MaxPermSize=1024m -Xss2m -server"
JAVACMD="java $JAVA_OPTS -classpath conf:$cp com.davcai.data.statistic.WeiboStatisticTaskLauncher"
$JAVACMD 2>/data/logs/data-statistic/data-statistic-error.log