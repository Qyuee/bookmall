package com.cafe24.bookmall.daoTest;

import java.util.List;

import com.cafe24.bookmall.dao.CategoryDao;
import com.cafe24.bookmall.vo.CategoryVo;

public class CategoryDaoTest {
	public static void main(String[] args) {
		//insertCategory("테스트1");  // ok!
		readCategory();
	}
	
	public static void insertCategory(String name) {
		CategoryDao dao=new CategoryDao();
		dao.insertCategory(name);
	}
	
	public static void readCategory() {
		CategoryDao dao=new CategoryDao();
		List<CategoryVo> list=dao.readCategoryList();
		CategoryVo vo=null;
		
		System.out.println("=====카테고리 목록=====");
		for(int i=0; i<list.size(); i++) {
			vo=list.get(i);
			System.out.println((i+1)+". "+vo.getName());
		}
	}
}
