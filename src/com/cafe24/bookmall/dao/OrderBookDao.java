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
import com.cafe24.bookmall.vo.OrderBookVo;

public class OrderBookDao {
	
	public List<OrderBookVo> readOrderBookList(String OrderKey){
		List<OrderBookVo> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" select ob.bookno, b.title, ob.price, ob.qty from order_book ob, book b where (ob.bookno=b.bookno)"
					+ "and ob.orderno= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, OrderKey);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				OrderBookVo vo=new OrderBookVo();
				vo.setBookNo(rs.getInt(1));
				vo.setbTitle(rs.getString(2));
				vo.setPrice(rs.getInt(3));
				vo.setQty(rs.getInt(4));
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return list;
	}
	
	// 주문 도서 리스트 작성.
	public boolean insertOrderBook(String OrderKey, int bookNo, int qty, int price) {
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			
			String sql="insert into order_book values( ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			pstmt.setString(2, OrderKey);
			pstmt.setInt(3, qty);
			pstmt.setInt(4, price);
			
			int count=pstmt.executeUpdate();
			
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
	
}
