package com.cafe24.bookmall.app;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import com.cafe24.bookmall.dao.BookDao;
import com.cafe24.bookmall.dao.CartDao;
import com.cafe24.bookmall.dao.CategoryDao;
import com.cafe24.bookmall.dao.MemberDao;
import com.cafe24.bookmall.dao.OrderBookDao;
import com.cafe24.bookmall.dao.OrderDao;
import com.cafe24.bookmall.daoTest.BookDaoTest;
import com.cafe24.bookmall.vo.BookVo;
import com.cafe24.bookmall.vo.CategoryVo;
import com.cafe24.bookmall.vo.MemberVo;
import com.cafe24.bookmall.vo.OrderBookVo;
import com.cafe24.bookmall.vo.OrderVo;
import com.cafe24.bookmall.vo.ReadCartVo;

public class BookMallApp {
	public static void main(String[] args) {
		// 회원은 1번 회원 '이동석'이라 한다. (가정)
		// 1. 책 목록 조회
		// 2. 책 선택.
		// 3. 장바구니 넣기.
		
//	한 번만 실행 해주세요.	
		
//		// ====================== 데이터 생성 ====================================
//		// 회원 생성.
//		insertMember("이동석", "010-0000-0000", "lee33398@naver.com", "1234");
//		insertMember("안대혁", "010-0000-0000", "kickscar@gmail.com", "1234");
//		
//		// 카테고리 생성
//		insertCategory("SF/공상과학");  // ok!
//		insertCategory("멜로/로맨스");  // ok!
//		insertCategory("판타지");  // ok!
//		
//		// 책 목록 생성
//		insertBook("우주어딘가에서", 11000, 1);
//		insertBook("인간, 달에가다.", 13000, 1);
//		insertBook("해를 품은 달(상권)", 15000, 2);
//		insertBook("해를 품은 달(하권)", 17000, 2);
//		insertBook("해리포터와 마법사의 돌", 20000, 3);
//		insertBook("해리포터와 불사조 기사단", 19000, 3);
//		
//		// ===================================================================
//		
// 한 번만 실행 해주세요.
		
		
		int memno=0;
		Scanner sc=new Scanner(System.in);
		MemberDao memberDao=new MemberDao();
		
		// 로그인
		while(true){
			System.out.println("=======로그인=======");
			System.out.print("이메일: ");
			String email=sc.nextLine();
			System.out.print("비밀번호: ");
			String password=sc.nextLine();
			
			memno=memberDao.login(email, password);
			if(memno==0){
				System.out.println("==아이디나 비밀번호가 틀립니다.\n\n");
				continue;
			}
			
			break;
		}
		
		while(true) {
			
			System.out.println("\n\n ========[메뉴]=========");
			System.out.println("1. 책 목록 조회");
			System.out.println("2. 장바구니 보기");
			System.out.println("3. 주문 목록 보기");
			System.out.println("4. 회원 목록 보기");
			System.out.println("5. 카테고리 목록 보기");
			System.out.println("(종료 -> 0입력)"); 
			
			System.out.print(">>");
			int select=sc.nextInt();
			BookDao bookDao=new BookDao();
			
			sc.nextLine();
			
			switch (select) {
			case 1: 
				BookDaoTest.readBookList();
				System.out.println("▶▶책 번호를 선택하세요. (0:메뉴가기) ");
				System.out.print("[책 번호 입력]>>");
				int selectBook=sc.nextInt();
				
				if(selectBook==0){
					break;
				}
				
				BookVo vo=new BookVo();
				vo=bookDao.readBookSelected(selectBook);
				
				System.out.println("▶▶몇 권을 [주문] or [장바구니넣기]하시겠습니까? ");
				System.out.print("[수량 입력]>>");
				int qty=sc.nextInt();
				
				System.out.println("▶▶[1: 바로주문  or 2: 장바구니넣기] 선택해주세요.");
				System.out.print("[동작 입력]>>");
				int cart_or_buy=sc.nextInt();
				
				if(selectBook!=0 && selectBook > 0 && cart_or_buy==2) {
					insertCart(vo, memno, qty);
					break;
				} else if(selectBook==0) {
					break;
				} else if(cart_or_buy==1) { // 바로 주문.
					orderProcess(memno, vo, qty);  // vo는 선택한 책
					break;
				}
			
			case 2: // 장바구니 조회.
				List<ReadCartVo> list= readCart(memno);
				if(list.isEmpty()){
					break;
				}
				
				System.out.print("\n■ 장바구니 목록을 주문하시겠습니까?(y:주문하기 / n:메뉴)");
				String order_start=sc.nextLine();
				
				if(order_start.equals("y")) {
					orderProcess(memno, list);
					// 장바구니 list 삭제 프로세스.
				}
				break;
			
			case 3: // 주문 목록 조회
				orderconfirm(memno);
				break;
				
			case 4: // 회원 목록 보기
				readMemberList();
				break;
			
			case 5: //카테고리 목록 보기
				readCategory();
				break;
	
			case 0:
				System.out.println("종료합니다.");
				break;
			}
			
			if(select==0) {
				break;
			}
			
		}
		sc.close();
	}
	
	// 카테고리 생성.
	public static void insertCategory(String name) {
		CategoryDao dao=new CategoryDao();
		dao.insertCategory(name);
	}
	
	// 카테고리 목록 보기
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
	
	// 책 생성
	public static void insertBook(String title, int price, int cateno) {
		BookDao dao=new BookDao();
		dao.insertBook(title, price, cateno);
	}
	
	// 회원 목록 보기
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
	
	// 회원 생성.
	public static void insertMember(String name, String tel, String email, String password) {
		MemberDao dao=new MemberDao();
		if(dao.insertMember(name, tel, email, password)==true) {
			System.out.println("[★★★회원생성에 성공하였습니다.★★★]");
		}
	}
	
	// 장바구니 등록
	public static void insertCart(BookVo vo, int memno, int qty) {
		CartDao dao=new CartDao();
		
		if(dao.CheckCart(vo.getBookNo(), memno)==false){
			// 장바구니에 추가.
			if(dao.insertCart(vo, memno, qty)) {
				System.out.println("\n\n▶▶책을 장바구니에 저장하였습니다.\n\n");
			}else {
				System.out.println("\n\n▶▶책을 장바구니에 저장 실패.....\n\n");
			}
		} else {
			// 장바구니에서 해당 책 수량 변경.
			System.out.println("\n\n▶▶ 장바구니에 이미 해당 제품이 있습니다. 덮어서 저장합니다.");
			dao.UpdateCartCnt(vo.getBookNo(), memno, qty);
		}
		
	}
	
	// 장바구니 조회
	public static List<ReadCartVo> readCart(int memberNo) { 
		DecimalFormat df=new DecimalFormat("###,### 원");
		CartDao dao=new CartDao();
		List<ReadCartVo> list=dao.readCartList(memberNo);
		ReadCartVo vo=null;
		int total=0;
		
		if(list.isEmpty()){
			System.out.println("\n\n\t[장바구니에 선택된 상품이 없습니다]\n\n");
			return list;
		}
		
		System.out.println("\n\n=========================[장바구니 목록]=========================");
		for(int i=0; i<list.size(); i++) {
			vo=list.get(i);
			System.out.println((i+1)+"번. 책 이름:"+vo.getbTitle()+"\t 책 가격:"+df.format(vo.getbPrice())+"\t 수량:"+vo.getcQty()+"\t 가격:"+vo.getSub_total());
			total+=vo.getSub_total();
		}
		System.out.println("총 가격: "+df.format(total));
		System.out.println("==============================================================\n\n");
		
		return list;
	}
	 
	// 바로 주문.
	public static void orderProcess(int memno, BookVo vo, int qty) {
		// 주문서 만들기
		Scanner sc=new Scanner(System.in);
		System.out.println("\n\n======주문서 쓰기=======");
		System.out.println("▶▶주소를 입력하세요.");
		System.out.print("[주소 입력] :");
		String addr=sc.nextLine();
		
		OrderDao dao=new OrderDao();
		String Order_Key=dao.createOrder(memno, addr);  // 주문서 내역 생성 + key 반환.
		
		// 주문도서 목록 만들기 
		OrderBookDao orderbookDao=new OrderBookDao();
		orderbookDao.insertOrderBook(Order_Key, vo.getBookNo(), qty, vo.getPrice());

		dao.updateOrderPrice(Order_Key);
		
		System.out.println("\n\n================");
		System.out.println("▶▶바로 주문완료 입니다!");
		System.out.println("=================");
		
	}
	
	// 카트 -> 주문으로
	public static void orderProcess(int memno, List<ReadCartVo> list) {
		// 주문서 만들기
		Scanner sc=new Scanner(System.in);
		System.out.println("\n\n======주문서 쓰기=======");
		System.out.println("▶▶주소를 입력하세요.");
		System.out.print("[주소 입력] :");
		String addr=sc.nextLine(); 
		
		OrderDao dao=new OrderDao();
		CartDao cartDao=new CartDao();
		
		// 주문서 목록 만들기.
		OrderBookDao orderbookDao=new OrderBookDao();
		String Order_Key=dao.createOrder(memno, addr);  // 주문서 내역 생성 + key 반환.
		
		for(int i=0; i<list.size(); i++) { 
			ReadCartVo vo=list.get(i);
			orderbookDao.insertOrderBook(Order_Key, vo.getBookno(), vo.getcQty(), vo.getbPrice());
		}
		
		dao.updateOrderPrice(Order_Key);  // 주문서 총 금액 갱신
		cartDao.deleteCartAfterOrder(memno);  // 카트 목록 삭제
		
		System.out.println("\n\n================");
		System.out.println("▶▶장바구니 주문완료 입니다!");
		System.out.println("=================");
	}
	
	// 주문서 확인하기
	public static void orderconfirm(int memno) {
		DecimalFormat df=new DecimalFormat("###,### 원");
		OrderDao orderDao=new OrderDao();
		List<OrderVo> list = orderDao.readOrderList(memno);
		OrderVo vo=null;
		OrderBookDao orderBookDao=new OrderBookDao();
		List<OrderBookVo> orderBookList=null;
		OrderBookVo orderBookVo=null;
		
		if(list.isEmpty()){
			System.out.println("\n\n\t[현재 주문이 없습니다]\n\n");
			return;
		}
		
		System.out.println("\n\n================주문 확인=================");
		System.out.println("=======================================");
		for(int i=0; i<list.size(); i++) {
			vo=list.get(i);
			
			System.out.println("["+(i+1)+"번 주문]");
			System.out.println("주문자 : "+vo.getmName()+"\t 주문번호 : "+vo.getOrderNo());
			System.out.println("배송지 : "+vo.getAddr());
			// ===주문 도서 리스트===
			orderBookList=orderBookDao.readOrderBookList(vo.getOrderNo());
			for(int j=0; j<orderBookList.size(); j++){
				orderBookVo=orderBookList.get(j);
				System.out.println("["+(j+1)+"번 책  정보] "+"책 이름: "+orderBookVo.getbTitle()+"  가격: "+df.format(orderBookVo.getPrice())+"  수량:"+orderBookVo.getQty());
			}
			
			// =================
			System.out.println("총 금액 : "+df.format(vo.getPrice()));
			System.out.println("\n");
		}
		
		System.out.println("=======================================");
		System.out.println("=======================================");
	}
}
