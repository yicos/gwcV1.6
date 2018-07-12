package com.utils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class WebUtils {
	public static JSONObject formatDateByBikePoiRs(CommonRsp rsp) {
		try {
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(java.util.Date.class,
					new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonObject = JSONObject.fromObject(rsp, config);
			
			return jsonObject;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return JSONObject.fromObject(rsp);
	}
	
}
