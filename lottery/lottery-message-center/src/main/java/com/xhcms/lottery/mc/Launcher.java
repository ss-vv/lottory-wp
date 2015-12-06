package com.xhcms.lottery.mc;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Launcher {

    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    private static final String PID_FILE = "/dev/shm/mc";
    
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
        System.out.println("Lottery message center started.");
        log.info("Lottery message center started.");

        File file = new File(PID_FILE);
        while(file.exists()){
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
            }
        }
        log.info("Lottery message center stopping...");
        context.destroy();
        log.info("Lottery message center stopped.");
        
        System.out.println("Lottery message center stopped.");
    }

}
