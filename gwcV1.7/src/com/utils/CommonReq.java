package com.utils;

import java.io.Serializable;

import com.constant.AppConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 基础请求公共类
 * @author Administrator
 *
 */
public class CommonReq implements Serializable{
	private static final long serialVersionUID = 4707646962751180070L;
	
	/**命令代码**/
	private String Cmd;
	/**公共密匙**/
	private String Key=AppConstant.KEY;
	/**平台id**/
	private String PlatformID=AppConstant.PLATFORMID;
	/**参数内容 list<object>或者object**/
	private Object Args;
	/**签名**/
	private String Sign;
	
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
	public CommonReq(String cmd, String key, String platformID, Object args) throws Exception {
		super();
		Cmd = cmd;
		Key = key;
		PlatformID = platformID;
		Args = args;
		Sign = getSign();
	}
	public CommonReq() {
		super();
	}
	public CommonReq(String cmd, Object args) throws Exception {
		super();
		Cmd = cmd;
		Args = args;
		Sign = getSign();
	}
	/**
	 * post请求参数
	 * @return
	 */
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
	/**
	 * 应对接口文档V1.1.0 ：批量接口增加 POST 兼容，解决 URL 参数过长问题
	 * ?cmd=cmd&PlatformID=PlatformID&&Args=
	 * @return
	 */
	public String toUrlGetParams(){
		StringBuffer stringBuffer = new StringBuffer();
		//参数为空
		stringBuffer.append("?Cmd="+this.Cmd+"&Key="+this.Key+"&Args=&PlatformID="
		+this.PlatformID+"&Sign="+this.Sign);
		return stringBuffer.toString();
	}
	
	/**
	 * 应对接口文档V1.1.0 ：批量接口增加 POST 兼容，解决 URL 参数过长问题
	 * 获取args参数
	 * @return
	 */
	public String getArgsJsonStr(){
		String jsonStr=null;
		if(this.Args instanceof java.util.List){
			jsonStr= JSONArray.fromObject(Args).toString().trim();
		}else  {
			jsonStr = JSONObject.fromObject(Args).toString().trim();
		}
		return jsonStr;
	}
}
