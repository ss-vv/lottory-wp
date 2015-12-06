package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.sql.DataSource;

import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;

public class ConnectionPool {
    private Vector<Connection> pool;
    private static String url;
    private static String username;
    private static String password;
    private static String driverClassName;
    private int minPoolSize = 1;
	private int maxPoolSize = 20;
    private static ConnectionPool instance = null;
    private int countPoolSize = 0; //当前连接池中的链接个数
    
    //私有构造方法，禁止外部创建本类的对象，要想获得本类的对象，通过<code>getInstance</code>方法
    private ConnectionPool(){
//        System.out.println("构造函数");
        init();
    }
    
    //连接池初始化方法，读取属性文件的内容，建立连接池中的初始连接
    private void init(){
        readConfig();
        pool = new Vector<Connection>(minPoolSize);
        addConnection();
    }
    
    //返回连接到连接池中
    public synchronized void release(Connection coon){
        pool.add(coon);
//		if (pool.size() == 1) {
			notify(); // 唤醒阻塞的线程
//		}
    }
    
    //关闭连接池中的所有数据库连接
    public synchronized void closePool(){
        for (int i = 0; i < pool.size(); i++) {
            try {
                ((Connection)pool.get(i)).close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            countPoolSize--; //防止出现死锁
            pool.remove(i);
        }
    }
    
    //返回当前连接池的一个对象
    public static ConnectionPool getInstance(){
        if (instance == null) {
        	instance = new ConnectionPool();
        }
        return instance;
    }
    
    //返回连接池中的一个数据库连接
    public synchronized Connection getConnection(){
    	Connection conn = null;
		do {
			if (pool.size() > 0) {
				conn = pool.get(0);
				pool.remove(conn);
			} else {
				if(countPoolSize<maxPoolSize){ //当前连接数小于最大链接个数
					conn = addNewConnection();
					countPoolSize++;
				} else {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} while (conn == null);
        return conn;
    }
    
    private Connection addNewConnection() {
    	Connection coon = null;
    	try {
            Class.forName(driverClassName);
            coon = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return coon;
	}

	//在连接池中创建初始设置的数据库连接
    private void addConnection(){
        Connection coon = null;
        for (int i = 0; i < minPoolSize; i++) {
            try {
                Class.forName(driverClassName);
                coon = DriverManager.getConnection(url, username, password);
                pool.add(coon);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        countPoolSize = minPoolSize;
    }
    
    //读取设置连接池的属性文件
    private void readConfig(){
        ConnectionPool.driverClassName = SystemPropertiesUtil.getPropsValue("driverClassName").trim();
        ConnectionPool.username = SystemPropertiesUtil.getPropsValue("userName").trim();
        ConnectionPool.password = SystemPropertiesUtil.getPropsValue("password").trim();
        ConnectionPool.url = SystemPropertiesUtil.getPropsValue("url").trim();
        this.minPoolSize = Integer.parseInt(SystemPropertiesUtil.getPropsValue("minPoolSize").trim());
        this.maxPoolSize  = Integer.parseInt(SystemPropertiesUtil.getPropsValue("maxPoolSize").trim());
    }
    
}