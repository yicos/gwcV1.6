package com.constant;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;

import com.request.TaxiModel;
import com.request.TaxiType;
import com.utils.PropertiesUtil;

public class AppConstant {
	public static SqlSessionFactory sessionFactory = null;
	public static boolean isRun = false;
	public static boolean autoRun = false;
	public static boolean VEHICLEINFOS_BATCH = false;
	public static Map<String, TaxiType> taxiTypeMap = new HashMap<>();
	public static Map<String, TaxiModel> taxiModelMap = new HashMap<>();
	
	
	
	/** 3.2.3位置终端信息(批量) */
	public static String CMD_TERMINALINFOS;
	/** 3.2.3位置终端信息(批量)定时时间 */
	public static long TIMES_CMD_TERMINALINFOS;
	/** 3.2.3位置终端信息(批量)中文名称 */
	public static String NAME_CMD_TERMINALINFOS;

	/** 3.2.4组织机构信息 */
	public static String CMD_ORGANIZATIONINFO;
	/** 3.2.4组织机构信息定时时间 */
	public static long TIMES_CMD_ORGANIZATIONINFO;
	/** 3.2.4组织机构信息中文名称 */
	public static String NAME_CMD_ORGANIZATIONINFO;

	/** 3.2.6车辆信息(批量) */
	public static String CMD_VEHICLEINFOS;
	/** 3.2.6车辆信息(批量)定时时间 */
	public static long TIMES_CMD_VEHICLEINFOS;
	/** 3.2.6车辆信息(批量)中文名称 */
	public static String NAME_CMD_VEHICLEINFOS;

	/** 3.2.8司机信息(批量) */
	public static String CMD_DRIVERINFOS;
	/** 3.2.8司机信息(批量)定时时间 */
	public static long TIMES_CMD_DRIVERINFOS;
	/** 3.2.8司机信息(批量)中文名称 */
	public static final String NAME_CMD_DRIVERINFOS;

	/** 3.3.2位置数据接入(批量) */
	public static String CMD_UPLOADLOCATIONS;
	/** 3.3.2位置数据接入(批量)定时时间 */
	public static long TIMES_CMD_UPLOADLOCATIONS;
	/** 3.3.2位置数据接入(批量)中文名称 */
	public static String NAME_CMD_UPLOADLOCATIONS;

	/** 获取车辆品牌接口 */
	public static String GetTaxiType;
	/** 获取车辆品牌接口 */
	public static String GetTaxiModel;
	/** 获取车辆品牌接口 获取车辆品牌接口 定时时间 */
	public static long TIMES_CMD_GETTAXI;

	/** 动态密钥 */
	public static String KEY;

	/** 地市接入方账号 */
	public static String PLATFORMID;

	/** 加密时编码 */
	public static Charset CHARSET;

	/**
	 * #BasicDataManagement.ashx 基础数据接入管理 #LocationDataManagement.ashx 位置数据接入管理
	 * #VehicleManagement.ashx 用车业务数据接入管理 #VehicleServiceManagement.ashx 用车服务数据接入管理
	 */
	public static String POSTURL_BASIC;
	public static String POSTURL_LOCATION;

	/** des加密时key */
	public static String DESKEY;
	/** des加密时iv */
	public static String DESIV;

	static {
		// 连接数据库
		InputStream is;
		InputStreamReader inputStreamReader;
		Properties propertiesUtil =new Properties();
		try {
			//手动加载log4j配置文件
			PropertyConfigurator.configure("config/log4j.properties");
			
			
		    String mybatisPath = System.getProperty("user.dir")+ "\\config\\mybatis.xml";
		    String myAppproperties = System.getProperty("user.dir")+ "\\config\\app.properties";
		    is=new BufferedInputStream(new FileInputStream(mybatisPath));
		    // 创建SqlSessionFactory工厂
		    sessionFactory = new SqlSessionFactoryBuilder().build(is);
		    inputStreamReader = new InputStreamReader(new FileInputStream(myAppproperties),"UTF-8");
	        propertiesUtil.load(inputStreamReader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//PropertiesUtil propertiesUtil = new PropertiesUtil(myAppproperties);;
		CMD_TERMINALINFOS = propertiesUtil.getProperty("CMD_TERMINALINFOS");
		CMD_ORGANIZATIONINFO = propertiesUtil.getProperty("CMD_ORGANIZATIONINFO");
		CMD_VEHICLEINFOS = propertiesUtil.getProperty("CMD_VEHICLEINFOS");
		CMD_DRIVERINFOS = propertiesUtil.getProperty("CMD_DRIVERINFOS");
		CMD_UPLOADLOCATIONS = propertiesUtil.getProperty("CMD_UPLOADLOCATIONS");

		TIMES_CMD_TERMINALINFOS = Long.valueOf(propertiesUtil.getProperty("TIMES_CMD_TERMINALINFOS"));
		TIMES_CMD_ORGANIZATIONINFO = Long.valueOf(propertiesUtil.getProperty("TIMES_CMD_ORGANIZATIONINFO"));
		TIMES_CMD_VEHICLEINFOS = Long.valueOf(propertiesUtil.getProperty("TIMES_CMD_VEHICLEINFOS"));
		TIMES_CMD_DRIVERINFOS = Long.valueOf(propertiesUtil.getProperty("TIMES_CMD_DRIVERINFOS"));
		TIMES_CMD_UPLOADLOCATIONS = Long.valueOf(propertiesUtil.getProperty("TIMES_CMD_UPLOADLOCATIONS"));
		TIMES_CMD_GETTAXI = Long.valueOf(propertiesUtil.getProperty("TIMES_CMD_GETTAXI"));

		NAME_CMD_TERMINALINFOS = propertiesUtil.getProperty("NAME_CMD_TERMINALINFOS");
		NAME_CMD_ORGANIZATIONINFO = propertiesUtil.getProperty("NAME_CMD_ORGANIZATIONINFO");
		NAME_CMD_VEHICLEINFOS = propertiesUtil.getProperty("NAME_CMD_VEHICLEINFOS");
		NAME_CMD_DRIVERINFOS = propertiesUtil.getProperty("NAME_CMD_DRIVERINFOS");
		NAME_CMD_UPLOADLOCATIONS = propertiesUtil.getProperty("NAME_CMD_UPLOADLOCATIONS");

		KEY = propertiesUtil.getProperty("KEY");
		PLATFORMID = propertiesUtil.getProperty("PLATFORMID");
		CHARSET = Charset.forName(propertiesUtil.getProperty("CHARSET"));
		POSTURL_BASIC = propertiesUtil.getProperty("POSTURL_BASIC");
		POSTURL_LOCATION = propertiesUtil.getProperty("POSTURL_LOCATION");

		DESKEY = propertiesUtil.getProperty("DESKEY");
		DESIV = propertiesUtil.getProperty("DESIV");

		GetTaxiType = propertiesUtil.getProperty("GetTaxiType");
		GetTaxiModel = propertiesUtil.getProperty("GetTaxiModel");
		
		if("1".equals(propertiesUtil.getProperty("autoRun"))) {
			autoRun=true;
		}else {
			autoRun=false;
		}
		if("1".equals(propertiesUtil.getProperty("VEHICLEINFOS_BATCH"))) {
			VEHICLEINFOS_BATCH=true;
		}else {
			VEHICLEINFOS_BATCH=false;
		}
		
	}

}
