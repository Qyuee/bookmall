package com.cafe24.bookmall.daoTest;

import java.text.DecimalFormat;
import java.util.List;

import com.cafe24.bookmall.dao.BookDao;
import com.cafe24.bookmall.vo.BookVo;

public class BookDaoTest {
	public static void main(String[] args) {
		//insertBook("테스트 북1", 1000, 1);
		readBookList();
	}
	
	
	public static void insertBook(String title, int price, int cateno) {
		BookDao dao=new BookDao();
		dao.insertBook(title, price, cateno);
	}
	
	public static void readBookList() {
		DecimalFormat df=new DecimalFormat("###,### 원");
		BookDao dao=new BookDao();
		List<BookVo> list=dao.readBookList();
		BookVo vo=new BookVo();
		
		System.out.println("======================[도서 목록]========================");
		for(int i=0; i<list.size(); i++) {
			vo=list.get(i);
			System.out.println(vo.getBookNo()+". "+vo.getTitle()+"\t"+df.format(vo.getPrice())+" \t"+vo.getCateName());
		}
		System.out.println("======================================================"); 
	}
}
