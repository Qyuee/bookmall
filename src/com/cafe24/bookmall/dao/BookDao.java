package com.cafe24.bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.bookmall.DB.DBClose;
import com.cafe24.bookmall.DB.DBOpen;
import com.cafe24.bookmall.vo.BookVo;

public class BookDao {
	
	/**
	 * 선택한 책의 정보 반환.
	 * @param bookno
	 * @return
	 */
	public BookVo readBookSelected(int bookno){
		BookVo vo=new BookVo();
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = DBOpen.getConnection();
			String sql = "select b.bookno, b.title, b.price, c.name\r\n" + 
					" from book b, category c\r\n" + 
					" where (b.cateno=c.cateno) and bookno = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookno);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				vo=new BookVo();
				vo.setBookNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPrice(rs.getInt(3));
				vo.setCateName(rs.getString(4));
			}
			
			System.out.println("\n\n=============선택 한 책 정보=============");
			System.out.println("책 번호 :"+vo.getBookNo());
			System.out.println("책 이름 :"+vo.getTitle());
			System.out.println("책 가격 :"+vo.getPrice());
			System.out.println("장르 :"+vo.getCateName());
			System.out.println("====================================\n\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return vo;
	}

	/**
	 * 도서 목록 조회
	 * @return list
	 */
	public List<BookVo> readBookList(){
		List<BookVo> list=new ArrayList<BookVo>();
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn = DBOpen.getConnection();
			String sql = "select b.bookno, b.title, b.price, c.name\r\n" + 
					"from book b, category c\r\n" + 
					"where (b.cateno=c.cateno) order by b.bookno asc";
			pstmt = conn.prepareStatement(sql);

			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BookVo vo=new BookVo(); 
				vo.setBookNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPrice(rs.getInt(3));
				vo.setCateName(rs.getString(4));
				
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
	 * 서적 입력.
	 * 
	 * @param title
	 * @param price
	 * @param cateno
	 * @return boolean
	 */
	public boolean insertBook(String title, int price, int cateno) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBOpen.getConnection();

			String sql = "insert into book values(null, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setInt(2, price);
			pstmt.setInt(3, cateno);

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

}
