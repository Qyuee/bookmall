package com.cafe24.bookmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.cafe24.bookmall.DB.DBClose;
import com.cafe24.bookmall.DB.DBOpen;
import com.cafe24.bookmall.vo.OrderVo;

public class OrderDao {
	
	// order 테이블 총 금액 갱신
	/*
	 * Order테이블 생성 -> Order_book row 생성 -> Order_book테이블을 바탕으로 총 금액 update
	 */
	public boolean updateOrderPrice(String Order_Key){
		boolean result=false;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			
			String sql="update `order` set price=( select sum(qty*price) from order_book"
						+" where orderno= ? )"
						+" where orderno= ? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, Order_Key);
			pstmt.setString(2, Order_Key);
			int count=pstmt.executeUpdate();
			
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
	
	// Order 테이블 조회 -> key값이 필요함.
	public List<OrderVo> readOrderList(int memberno){
		List<OrderVo> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" select distinct m.name, o.orderno,  o.addr, o.price"
					+" from member m, `order` o"
					+" where (m.memno=o.memno)"
					+" and m.memno= ? ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, memberno);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				OrderVo vo=new OrderVo();
				vo.setmName(rs.getString(1));
				vo.setOrderNo(rs.getString(2));
				vo.setAddr(rs.getString(3));
				vo.setPrice(rs.getInt(4));
				
				list.add(vo);
		 	}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt, rs);
		}
		
		return list;
	}
	
	// Order 레코드 생성.
	public String createOrder(int memNo, String addr) {
		Calendar c=Calendar.getInstance();
		c.getTimeInMillis();
		 
		String order_key="OD"+Long.toString(c.getTimeInMillis())+Integer.toString(memNo);
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=DBOpen.getConnection();
			String sql=" insert into `order` values( ?, null, ?, ? ) ";
			pstmt=conn.prepareStatement(sql);
			// key= OD + 현재시간 밀리세컨드 + 회원번호
			pstmt.setString(1, order_key);
			pstmt.setString(2, addr);
			pstmt.setInt(3, memNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.DBclose(conn, pstmt);
		}
		
		return order_key;
	}
	
}
