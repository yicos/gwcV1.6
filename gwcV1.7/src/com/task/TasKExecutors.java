package com.task;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.constant.AppConstant;
import com.listener.LogListener;

public class TasKExecutors{
	private ScheduledExecutorService taskServices = Executors.newScheduledThreadPool(1);;
	private static Logger log = Logger.getLogger(TasKExecutors.class);

	private Runnable driverInfoRunable = null;
	private Runnable organizationInfoRunable = null;
	private Runnable terminalInfosRunable = null;
	private Runnable vehicleInfosRunable = null;
	private Runnable uploadLocationsRunableThread0 = null;
	private Runnable uploadLocationsRunableThread1 = null;
	private Runnable uploadLocationsRunableThread2 = null;
	private Runnable taxiModelAndTaxiTypeRunable = null;

	private ScheduledFuture<?> driverInfoFutrue, organizationInfoFutrue, terminalInfosFutrue, vehicleInfosFutrue,
			uploadLocationsFutrue0,uploadLocationsFutrue1,uploadLocationsFutrue2,taxiModelAndTaxiTypeFutrue;
	
	static SqlSessionFactory sessionFactory = null;
	

	static {
		// 连接数据库
		InputStream is;
		try {
		    String filePath = System.getProperty("user.dir")+ "\\config\\mybatis.xml";
		    System.out.println(filePath);
	        is=new BufferedInputStream(new FileInputStream(filePath));
			// 创建SqlSessionFactory工厂
			sessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 开启定时任务
	 */
	public void openThreadPool() {
		taxiModelAndTaxiTypeFutrue = taskServices.scheduleAtFixedRate(taxiModelAndTaxiTypeRunable, 1,
				AppConstant.TIMES_CMD_GETTAXI, TimeUnit.SECONDS);
		
		driverInfoFutrue = taskServices.scheduleAtFixedRate(driverInfoRunable, 1, AppConstant.TIMES_CMD_DRIVERINFOS,
				TimeUnit.SECONDS);
		organizationInfoFutrue = taskServices.scheduleAtFixedRate(organizationInfoRunable, 1,
				AppConstant.TIMES_CMD_ORGANIZATIONINFO, TimeUnit.SECONDS);
		terminalInfosFutrue = taskServices.scheduleAtFixedRate(terminalInfosRunable, 1,
				AppConstant.TIMES_CMD_TERMINALINFOS, TimeUnit.SECONDS);
		vehicleInfosFutrue = taskServices.scheduleAtFixedRate(vehicleInfosRunable, 1,
				AppConstant.TIMES_CMD_VEHICLEINFOS, TimeUnit.SECONDS);
		
		//开启三个处理位置信息
		uploadLocationsFutrue1 = taskServices.scheduleAtFixedRate(uploadLocationsRunableThread1, 1,
				AppConstant.TIMES_CMD_UPLOADLOCATIONS, TimeUnit.SECONDS);
		uploadLocationsFutrue0 = taskServices.scheduleAtFixedRate(uploadLocationsRunableThread0, 1,
				AppConstant.TIMES_CMD_UPLOADLOCATIONS, TimeUnit.SECONDS);
		uploadLocationsFutrue2 = taskServices.scheduleAtFixedRate(uploadLocationsRunableThread2, 1,
				AppConstant.TIMES_CMD_UPLOADLOCATIONS, TimeUnit.SECONDS);
		
		AppConstant.isRun=true;
		LogListener.warn(this.getClass(), "系统工具", "全部任务开始");
		
	}
	/**
	 * 关闭时停止两个线程 同时将线程池关闭
	 */
	public void closeThreadPool() {
		try {
			log.debug("开始关闭定时任务");
			stopSendOpPos(driverInfoFutrue);
			stopSendOpPos(vehicleInfosFutrue);
			stopSendOpPos(uploadLocationsFutrue0);
			stopSendOpPos(uploadLocationsFutrue1);
			stopSendOpPos(uploadLocationsFutrue2);
			stopSendOpPos(organizationInfoFutrue);
			stopSendOpPos(terminalInfosFutrue);
			stopSendOpPos(taxiModelAndTaxiTypeFutrue);
			taskServices.shutdownNow();
			final boolean done = taskServices.awaitTermination(15, TimeUnit.SECONDS);
			AppConstant.isRun=false;
			LogListener.warn(this.getClass(), "系统工具", "全部任务停止");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭某个定时任务
	 * 
	 * @param futrue
	 */
	private void stopSendOpPos(ScheduledFuture<?> futrue) {
		if (futrue != null && !futrue.isCancelled()) {
			futrue.cancel(true);
		}
	}

	public void init() {
		log.warn("开始初始化定时任务");

		driverInfoRunable = new DriverInfoRunable(sessionFactory);
		organizationInfoRunable = new OrganizationInfoRunable(sessionFactory);
		terminalInfosRunable = new TerminalInfosRunable(sessionFactory);
		vehicleInfosRunable = new VehicleInfosRunable(sessionFactory);
		uploadLocationsRunableThread0 = new UploadLocationsRunable(sessionFactory,"0");
		uploadLocationsRunableThread1 = new UploadLocationsRunable(sessionFactory,"1");
		uploadLocationsRunableThread2 = new UploadLocationsRunable(sessionFactory,"2");
		taxiModelAndTaxiTypeRunable= new GetTaxiModelAndTaxiTypeRunable();
		openThreadPool();
		
		log.warn("成功初始化定时任务");
		
	}

}
