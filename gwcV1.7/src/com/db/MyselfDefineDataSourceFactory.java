package com.db;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class MyselfDefineDataSourceFactory extends UnpooledDataSourceFactory {
	public MyselfDefineDataSourceFactory() {
		this.dataSource = new DruidDataSource();
	}
}
