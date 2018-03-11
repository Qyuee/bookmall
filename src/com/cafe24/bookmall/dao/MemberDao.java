package com.cafe24.bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.bookmall.DB.DBClose;
import com.cafe24.bookmall.DB.DBOpen;
import com.cafe24.bookmall.vo.MemberVo;

public class MemberDao {
	

	/**
	 * 로그인
	 * @param email
	 * @param password
	 * @return
	 */
	public int login(String email, String password){
		Integer count=new Integer(0);
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = DBOpen.getConnection();
			String sql="select memno "
					+" from member"
					+" where email= ? and password= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				if((Integer)rs.getInt(1)!=null)
				count=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * 회원 목록 출력
	 * @return
	 */
	public List<MemberVo> readMemberList(){
		List<MemberVo> list=new ArrayList<>();
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = DBOpen.getConnection();
			String sql = "select name, tel, email from member";
			pstmt = conn.prepareStatement(sql);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVo vo=new MemberVo(); 
				vo.setName(rs.getString(1));
				vo.setTel(rs.getString(2));
				vo.setEmail(rs.getString(3));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return list;
	}
	
	/**
	 * 회원 가입
	 * @param name
	 * @param tel
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean insertMember(String name, String tel, String email, String password) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try { 
			conn= DBOpen.getConnection();
			
			String sql="insert into member values(null, ?, ?, ?, ?);";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, tel);
			pstmt.setString(3, email);
			pstmt.setString(4, password);
			
			if(pstmt.executeUpdate()==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		
		return result;
	}
	
}
