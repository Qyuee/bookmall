package com.cafe24.bookmall.daoTest;

import java.util.List;

import com.cafe24.bookmall.dao.MemberDao;
import com.cafe24.bookmall.vo.MemberVo;

public class MemberDaoTest {
	public static void main(String[] args) {
		
		//insertMember("둘리", "010-0000-0000", "dooly@naver.com", "1234");
		readMemberList();
	}
	
	public static void readMemberList() {
		List<MemberVo> list=null;
		MemberDao dao=new MemberDao();
		MemberVo vo=new MemberVo();
		list=dao.readMemberList();
		
		System.out.println("======================[회원 목록]========================");
		for(int i=0; i<list.size(); i++) {
			vo=list.get(i);
			System.out.println((i+1)+". 이름:"+vo.getName()+"     tel:"+vo.getTel()+"  email:"+vo.getEmail());
		}
		System.out.println("======================================================");
	}
	
	public static void insertMember(String name, String tel, String email, String password) {
		MemberDao dao=new MemberDao();
		if(dao.insertMember(name, tel, email, password)==true) {
			System.out.println("[★★★회원가입에 성공하였습니다.★★★]");
		}
	}
}
