package com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  GBAreaCode {
		shixiaqu("市辖区","440100"),
		liwangqu("荔湾区","440103"),
		yuexiuqu("越秀区","440104"),
		haizhuqu("海珠区","440105"),
		tianhequ("天河区","440106"),
		baiyunqu("白云区","440111"),
		huangpuqu("黄埔区","440112"),
		panyuqu ("番禺区","440113"),
		huaduqu ("花都区","440114"),
		nanshaqu("南沙区","440115"),
		luogangqu("萝岗区","440116"),
		zengcheng("增城市","440117"),
		conghuaqu("从化市","440118");
	
		
	public static List<String> showNameList=new ArrayList<String>();;
	public static List<String> showCodeList=new ArrayList<>();
	public static Map<String, String> showMap=new HashMap<>();
	static {
		for(GBAreaCode gbAreaCode:values()) {
			showNameList.add(gbAreaCode.getName());
			showCodeList.add(gbAreaCode.getCode());
			showMap.put(gbAreaCode.getName(), gbAreaCode.getCode());
		}
	}
	
	
	private String name;
	private String code;
	private GBAreaCode(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

		
}
