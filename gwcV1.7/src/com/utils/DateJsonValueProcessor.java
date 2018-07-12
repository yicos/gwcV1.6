package com.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {
	public static final String Default_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	// public static final String Default_DATE_PATTERN ="HH:mm:ss";
	private DateFormat dateFormat;

	public DateJsonValueProcessor(String datePattern) {
		try {
			dateFormat = new SimpleDateFormat(datePattern);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
		}
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	@Override
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		if(value == null){
			return "";
		}
		return dateFormat.format((Date) value);
	}

	public static JSONArray beanToJson(Object obj, String dateFormat) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class,
				new DateJsonValueProcessor(dateFormat));
		JSONArray json = JSONArray.fromObject(obj, config);
		return json;
	}

}