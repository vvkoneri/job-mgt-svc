package com.pyr.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class H2DbClient {

	
	@Value("${spring.datasource.url}")
    private String jdbcUrl;
	
	
	private static String JDBC_URL;
	
	@Value("${spring.datasource.url}")
	public void setUrlStatic(String jdbcUrl){
		H2DbClient.JDBC_URL = jdbcUrl;
    }
	
	@Value("${spring.datasource.username}")
    private String jdbcUsername;
	
	
	private static String JDBC_USER;
	
	@Value("${spring.datasource.username}")
	public void setUserStatic(String jdbcUsername){
		H2DbClient.JDBC_USER = jdbcUsername;
    }
	
	@Value("${spring.datasource.password}")
    private String jdbcPwd;
	
	
	private static String JDBC_PWD;
	
	@Value("${spring.datasource.password}")
	public void setPwdStatic(String jdbcPwd){
		H2DbClient.JDBC_PWD = jdbcPwd;
    }
	
	@Value("${spring.datasource.driverClassName}")
    private String jdbcDriver;
	
	
	private static String JDBC_DRIVER;
	
	@Value("${spring.datasource.driverClassName}")
	public void setDriverStatic(String jdbcDriver){
		H2DbClient.JDBC_DRIVER = jdbcDriver;
    }
	

	public  Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = initiateConnection();
		return conn;
	}

	private Connection initiateConnection() throws ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER);
		Connection conn = DriverManager.getConnection(JDBC_URL,
				JDBC_USER, JDBC_PWD);
		return conn;
	}
	

}
