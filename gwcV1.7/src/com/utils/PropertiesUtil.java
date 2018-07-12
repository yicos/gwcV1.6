package com.utils;

import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties文件加载
 * 
 * @author Administrator
 *
 */
public class PropertiesUtil {
	private static Properties props;

	public PropertiesUtil(String fileName) {
		readProperties(fileName);
	}

	/**
	 * 读取Properties文件
	 * 
	 * @param fileName
	 */
	private void readProperties(String fileName) {
		try {
			props = new Properties();
			InputStreamReader inputStream = new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8");
			props.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取key
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return props.getProperty(key);
	}

	/**
	 * 获取map
	 * 
	 * @return
	 */
	public Map<?, ?> getAll() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> enu = props.propertyNames();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = props.getProperty(key);
			map.put(key, value);
		}
		return map;
	}

}
