package com.cafe24.bookmall.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * DB 자원 종료 클래스
 * @author lee33
 *
 */
public class DBClose {
	
	/**
	 * conn, pstmt 자원 정리
	 * @param conn
	 * @param pstmt
	 */
	public static void DBclose(Connection conn, PreparedStatement pstmt){
		try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * conn, pstmt, rs 자원 정리
	 * @param conn
	 * @param pstmt
	 * @param rs
	 */
	public static void DBclose(Connection conn, PreparedStatement pstmt, ResultSet rs){
		try {
			if (rs!=null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * conn, stmt 자원 정리
	 * @param conn
	 * @param stmt
	 */
	public static void DBclose(Connection conn, Statement stmt){
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
