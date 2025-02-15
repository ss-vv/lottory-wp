log4j.rootLogger = INFO,R,R1
log4j.logger.org.apache = ERROR 
log4j.logger.org.hibernate = ERROR
log4j.logger.org.springframework = ERROR
log4j.logger.com.xhcms = INFO
log4j.logger.com.unison = INFO

log4j.appender.R1 = org.apache.log4j.ConsoleAppender
log4j.appender.R1.Target = System.out
log4j.appender.R1.layout = org.apache.log4j.PatternLayout
log4j.appender.R1.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss} %t %p [%c{2}] - %m%n

log4j.appender.R=org.apache.log4j.DailyRollingFileAppender
log4j.appender.R.File=/data/logs/weibo-data.log
log4j.appender.R.DatePattern = '.'yyyy-MM-dd
log4j.appender.R.layout=org.apache.log4j.PatternLayout
log4j.appender.R.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %t %p [%c{2}] - %m%n