package com.utils;

import java.io.Serializable;

import com.constant.AppConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonRsp implements Serializable{
	public static final int TAG_SUCCESS = 1;
	public static final int TAG_FAIL = 0;
	
	private static final long serialVersionUID = 4707646962751180070L;
	
	String Cmd;
	String Key=AppConstant.KEY;
	String PlatformID=AppConstant.PLATFORMID;
	Object Args;
	String Sign;
	public String getCmd() {
		return Cmd;
	}
	public void setCmd(String cmd) {
		Cmd = cmd;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public String getPlatformID() {
		return PlatformID;
	}
	public void setPlatformID(String platformID) {
		PlatformID = platformID;
	}

	public Object getArgs() {
		return Args;
	}
	public String getSign() throws Exception {
		//this.Key=new Random().nextInt(1000000000)+"";
		
		if(null==Args) {
			return Sign;
		}
		//DES加密
		if(Args instanceof java.util.List){
			this.Sign = CommUtils.encrypt(JSONArray.fromObject(Args).toString().trim(), AppConstant.CHARSET, AppConstant.KEY,AppConstant.DESIV);
		}else  {
			this.Sign = CommUtils.encrypt(JSONObject.fromObject(Args).toString().trim(), AppConstant.CHARSET, AppConstant.KEY,AppConstant.DESIV);
		}
		//MD5加密
		this.Sign=CommUtils.Md5Encode(this.Sign, AppConstant.CHARSET);
		
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	public CommonRsp(String cmd, String key, String platformID, Object args) throws Exception {
		super();
		Cmd = cmd;
		Key = key;
		PlatformID = platformID;
		Args = args;
		Sign = getSign();
	}
	public CommonRsp() {
		super();
	}
	public CommonRsp(String cmd, Object args) throws Exception {
		super();
		Cmd = cmd;
		Args = args;
		Sign = getSign();
	}
	public String toUrlPostParams(){
		StringBuffer stringBuffer = new StringBuffer();
		//参数为空
		if(null==Args||"null".equals(Args)) {
			stringBuffer.append("Cmd="+this.Cmd+"&Key="+this.Key+"&PlatformID="+this.PlatformID+"&Sign="+this.Sign);
		}else if(Args instanceof java.util.List){//参数list
			stringBuffer.append("Cmd="+this.Cmd+"&Key="+this.Key+"&Args="+JSONArray.fromObject(this.Args).toString()+"&PlatformID="+this.PlatformID+"&Sign="+this.Sign);
		}else {//参数为单个
			stringBuffer.append("Cmd="+this.Cmd+"&Key="+this.Key+"&Args="+JSONObject.fromObject(this.Args).toString()+"&PlatformID="+this.PlatformID+"&Sign="+this.Sign);
		}
		return stringBuffer.toString();
	}
	public String toCarPostParams(){
		StringBuffer stringBuffer = new StringBuffer();
		//参数为空
		stringBuffer.append("?Cmd="+this.Cmd+"&Key="+this.Key+"&Args=&PlatformID="
		+this.PlatformID+"&Sign="+this.Sign);
		return stringBuffer.toString();
	}
	public String getArgsJsonStr(){
		String jsonStr=null;
		//DES加密
		if(Args instanceof java.util.List){
			jsonStr= JSONArray.fromObject(Args).toString().trim();
		}else  {
			jsonStr = JSONObject.fromObject(Args).toString().trim();
		}
		return jsonStr;
	}
}
