package com.cafe24.bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.bookmall.DB.DBClose;
import com.cafe24.bookmall.DB.DBOpen;
import com.cafe24.bookmall.vo.CategoryVo;

public class CategoryDao {

	/**
	 * 카테고리 입력
	 * 
	 * @param name
	 * @return
	 */
	public boolean insertCategory(String name) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBOpen.getConnection();
			String sql = "insert into category values(null, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);

			if (pstmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		return result;
	}
	
	/**
	 * 카테고리 목록보기
	 * @return list
	 */
	public List<CategoryVo> readCategoryList(){
		List<CategoryVo> list=new ArrayList<CategoryVo>();
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = DBOpen.getConnection();
			String sql = "select cateno, name from category";

			pstmt = conn.prepareStatement(sql);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryVo vo=new CategoryVo(); 
				vo.setCateno(rs.getInt(1));
				vo.setName(rs.getString(2));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return list;
	}

}
