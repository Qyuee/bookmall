package com.cafe24.bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cafe24.bookmall.DB.DBClose;
import com.cafe24.bookmall.DB.DBOpen;
import com.cafe24.bookmall.vo.BookVo;
import com.cafe24.bookmall.vo.ReadCartVo;

public class CartDao {
	
	/**
	 * 주문 후 카트 삭제.
	 * @param memno
	 * @return boolean
	 */
	public boolean deleteCartAfterOrder(int memno){
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		int count=0;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" delete from cart where memno= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, memno);
			count=pstmt.executeUpdate();
			
			if(count==1){
				result=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 장바구니에 선택한 제품의 수량 덮어쓰기.
	 * 이미 있는 도서에 대한 장바구니를 추가를 하였을 때.
	 * @param bookNo
	 * @param memNo
	 * @param qty
	 * @return
	 */
	public boolean UpdateCartCnt(int bookNo, int memNo, int qty) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			int count=0;
			conn=DBOpen.getConnection();
			
			String sql=" update cart " + 
					" set qty=? " + 
					" where bookno=? and memno=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, qty);
			pstmt.setInt(2, bookNo);
			pstmt.setInt(3, memNo);
			
			count=pstmt.executeUpdate();
			
			if(count==1) {
				result=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return result;
	}
	
	/**
	 * 현재 카트에 해당하는 책이 있는지?
	 * @param bookNo
	 * @param memNo
	 * @return
	 */
	public boolean CheckCart(int bookNo, int memNo) {
		boolean result=false;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			int count=0;
			conn=DBOpen.getConnection();
			
			String sql="select count(*) from cart where bookno= ? and memno= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			pstmt.setInt(2, memNo);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				count=rs.getInt(1);
			}
			
			if(count==1) { 
				result=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return result;
	}
	
	/**
	 * 장바구니 보기
	 * @param memNo
	 * @return
	 */
	public List<ReadCartVo> readCartList(int memNo){
		List<ReadCartVo> list=new ArrayList<>();
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBOpen.getConnection();
			
			String sql="select m.name, b.title, b.price, c.qty, (c.qty*b.price) as sub_total, b.bookno " + 
					" from book b, cart c, member m " + 
					" where (b.bookno=c.bookno) and (m.memno=c.memno) and c.memno=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReadCartVo vo=new ReadCartVo();
				vo.setmName(rs.getString(1));
				vo.setbTitle(rs.getString(2));
				vo.setbPrice(rs.getInt(3));
				vo.setcQty(rs.getInt(4));
				vo.setSub_total(rs.getInt(5));
				vo.setBookno(rs.getInt(6));
				
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
	 * 장바구니에 도서 등록하기
	 * @param vo
	 * @param memberNo
	 * @param qty
	 * @return
	 */
	public boolean insertCart(BookVo vo, int memberNo, int qty) {
		// 장바구니에 책을 등록한다.
		// 책 목록 보고 -> 책 선택 -> 장바구니에 등록
		// 세부 과정 : 책 번호를 통해 책 정보 확인 후 그 내용(bookno)을 cart 테이블에 넣는다.
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String key="C"+Integer.toString(vo.getBookNo())+Integer.toString(memberNo);
			String sql="insert into cart values(?, ?, ?, ?, ?);";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setInt(2, qty);
			pstmt.setInt(3, vo.getPrice()*qty);
			pstmt.setInt(4, memberNo);
			pstmt.setInt(5, vo.getBookNo());
			
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
