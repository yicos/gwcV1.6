package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.junit.experimental.theories.Theories;

import com.constant.AppConstant;
import com.listener.LogListener;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpUtils {
	/**
	 * HttpURLConnection的post请求，请求参数放在路径后面
	 * 
	 */
	public static String jsonPostParams(String strURL, String param) {
		try {
			URL url = new URL(strURL + param);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			// 设置文件类型:
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			// 设置接收类型否则返回415错误
			// conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
			connection.setRequestProperty("accept", "application/json");

			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.flush();
			out.close();
			int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码
				return result;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息
	}

	/**
	 * HttpURLConnection的post请求，请求参数放在body
	 * 
	 */
	public static String jsonPost(String strURL, String commonRsp) throws Exception {
		try {
			URL url = new URL(strURL);// 创建连接
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置是否向connection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			connection.setReadTimeout(4000);
			// 默认是 GET方式
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// 设置本次连接是否自动重定向
			connection.setInstanceFollowRedirects(true);
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			// 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
			// String content = "字段名=" + URLEncoder.encode("字符串值", "编码");
			out.append(commonRsp);
			out.flush();
			out.close();

			int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码
				return result;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}
		return null; // 自定义错误信息
	}
	/**
	 * HttpURLConnection的post请求，请求参数放在body
	 * 应对接口文档V1.1.0 ：批量接口增加 POST 兼容，解决 URL 参数过长问题
	 * 获取args参数
	 * @return
	 */
	public static String jsonPost(String strURL, CommonReq commonRsp) throws Exception {
		try {
			URL url = new URL(strURL+commonRsp.toUrlGetParams());// 创建连接
			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置是否向connection输出，因为这个是post请求，参数要放在 http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			connection.setReadTimeout(4000);
			// 默认是 GET方式
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			// 设置本次连接是否自动重定向
			connection.setInstanceFollowRedirects(true);
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			// 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
			// String content = "字段名=" + URLEncoder.encode("字符串值", "编码");
			
			out.append(commonRsp.getArgsJsonStr());
			
			out.flush();
			out.close();
			int code = connection.getResponseCode();
			InputStream is = null;
			if (code == 200) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码
				return result;
			}
			
		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		}
		return null; // 自定义错误信息
	}

	/**
	 * HttpURLConnection的get请求，请求参数放在路径后面
	 * 
	 */
	public static String get(String url, String param) {
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url + param);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
}
