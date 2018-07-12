package com.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.ui.LogPanel;

/***
 * 主要对日志界面的一种监听类 这是一个调用拆入日志的线程 1，传入LogPanel是日志现实的界面类 2.传入插入日志的类容
 * 
 * @author lhy
 * 
 */
public class LogListener {
	private static LogThread thread = null;

	/**
	 * 插入日志的类容 map的字段有：serverno,servername,logtype,loginfo serverno :服务器编号
	 * servername：服务器名称 logtype：日志类型，1是正常，2是错误 loginfo：日志信息
	 */
	private static Map map = new HashMap();

	/**
	 * 运行线程的主题 插入日志的类容 map的字段有：serverno,servername,logtype,loginfo 
	 * serverno:服务器编号 servername：服务器名称 logtype：日志类型，1是正常，2是错误 loginfo：日志信息
	 */
	public static void logInfo(Map logmap) {
		synchronized (map) {
			map = logmap;
			/**
			 * 插入日志
			 */
			LogPanel.InsertLog(map);
		}

	}
	
	public static void start(){
		thread = new LogThread();
		thread.setDaemon(true);
		thread.start();
	}
	
	public static void stop(){
		thread.end();
		thread = null;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean info(Class clasz, String servername, String message){
		if(thread == null){
			return false;
		}
		Map logmap = new HashMap();
		logmap.put("serverno",clasz.getName());
		logmap.put("servername",servername);
		logmap.put("logtype", "1");
		logmap.put("loginfo", message);
		
		thread.pushLog(logmap);
		Logger log = Logger.getLogger(clasz);
		log.info(logmap);
		
		return true;
	}
	@SuppressWarnings("unchecked")
	public static boolean debug(Class clasz, String servername, String message){
		if(thread == null){
			return false;
		}
		Map logmap = new HashMap();
		logmap.put("serverno",clasz.getName());
		logmap.put("servername",servername);
		logmap.put("logtype", "1");
		logmap.put("loginfo", message);
		
		Logger log = Logger.getLogger(clasz);
		log.debug(logmap);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean error(Class clasz, String servername, String message){
		if(thread == null){
			return false;
		}
		
		Map logmap = new HashMap();
		logmap.put("serverno",clasz.getName());
		logmap.put("servername",servername);
		logmap.put("logtype", "2");
		logmap.put("loginfo", message);
		
		thread.pushLog(logmap);
		Logger log = Logger.getLogger(clasz);
		log.error(logmap);
		return true;
	}
	@SuppressWarnings("unchecked")
	public static boolean warn(Class clasz, String servername, String message){
		if(thread == null){
			return false;
		}
		
		Map logmap = new HashMap();
		logmap.put("serverno",clasz.getName());
		logmap.put("servername",servername);
		logmap.put("logtype", "3");
		logmap.put("loginfo", message);
		
		thread.pushLog(logmap);
		Logger log = Logger.getLogger(clasz);
		log.error(logmap);
		return true;
	}

}

class LogThread extends Thread{
	public static Log log = LogFactory.getLog(LogThread.class);
	private boolean isRunning = true;
	private static Vector<Map> list = new Vector<Map>();
	
	public void pushLog(Map map){
		list.add(map);
	}
	
	public Map popLog(){
		if(list.size() <= 0){
			return null;
		} else {
			return list.remove(0);
		}
	}
	
	public void end(){
		isRunning = false;
		try {
			this.join();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void run(){
		
		while(isRunning){
			try {
				Map map = popLog();
				if(map == null){
					sleep(1000);
				} else{
					LogListener.logInfo(map);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
	}
}