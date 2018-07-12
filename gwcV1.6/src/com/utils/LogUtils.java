package com.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
	private Logger log;

	public LogUtils(String logName) {
		log = LogManager.getLogger(logName);
	}

	@SuppressWarnings("rawtypes")
	public LogUtils(Class className) {
		log = LogManager.getLogger(className.toString());
	}

	public void i(String message) {
		if (log.isInfoEnabled()) {
			log.info(message);
		}else{
			System.out.println("notInfoEnabled:" + message);
		}
	}

	public void d(String message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	public void e(String message) {
		log.error(message);
	}

	public void f(String message) {
		log.fatal(message);
	}

	public void w(String message) {
		//为了测试暂时
		log.info(message);
	}

}
