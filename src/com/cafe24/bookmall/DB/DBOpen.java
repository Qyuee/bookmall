package com.cafe24.bookmall.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpen {
	/**
	 * DB연결 => 중복 코드
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;

		// 1. 드라이버 로딩
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 2. 연결하기. 
		String url = "jdbc:mysql://localhost:3306/bookmall";
		conn = DriverManager.getConnection(url, "bookmall", "bookmall"); // url, user, passwd

		return conn;
	}
}
