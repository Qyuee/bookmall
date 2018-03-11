package com.cafe24.bookmall.daoTest;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import com.cafe24.bookmall.dao.BookDao;
import com.cafe24.bookmall.dao.CartDao;
import com.cafe24.bookmall.dao.OrderBookDao;
import com.cafe24.bookmall.dao.OrderDao;
import com.cafe24.bookmall.vo.BookVo;
import com.cafe24.bookmall.vo.OrderVo;
import com.cafe24.bookmall.vo.ReadCartVo;

public class OrderDaoTest {
	public static void main(String[] args) {
		// 주문서 만들기 -> orderProcess(int memno)
		// 상황. 1번 회원이 1번 책을 1권 '바로 주문'한다.
		
		//orderProcess(1, new BookDao().readBookSelected(1), 1);
		
		// 주문서 확인하기. 1번 회원의 주문 내역.
		orderconfirm(1);
	}

	// 바로 주문.
	public static void orderProcess(int memno, BookVo vo, int qty) {
		// 주문서 만들기
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\n======주문서 쓰기=======");
		System.out.println("▶▶주소를 입력하세요.");
		System.out.print("[주소 입력] :");
		String addr = sc.nextLine();

		OrderDao dao = new OrderDao();
		String Order_Key = dao.createOrder(memno, addr); // 주문서 내역 생성 + key 반환.

		// 주문도서 목록 만들기
		OrderBookDao orderbookDao = new OrderBookDao();
		orderbookDao.insertOrderBook(Order_Key, vo.getBookNo(), qty, vo.getPrice());

		dao.updateOrderPrice(Order_Key);

		System.out.println("\n\n================");
		System.out.println("▶▶바로 주문완료 입니다!");
		System.out.println("=================");

	}

	// 카트 -> 주문으로
	public static void orderProcess(int memno, List<ReadCartVo> list) {
		// 주문서 만들기
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\n======주문서 쓰기=======");
		System.out.println("▶▶주소를 입력하세요.");
		System.out.print("[주소 입력] :");
		String addr = sc.nextLine();

		OrderDao dao = new OrderDao();
		CartDao cartDao = new CartDao();

		// 주문서 목록 만들기.
		OrderBookDao orderbookDao = new OrderBookDao();
		String Order_Key = dao.createOrder(memno, addr); // 주문서 내역 생성 + key 반환.

		for (int i = 0; i < list.size(); i++) {
			ReadCartVo vo = list.get(i);
			orderbookDao.insertOrderBook(Order_Key, vo.getBookno(), vo.getcQty(), vo.getbPrice());
		}

		dao.updateOrderPrice(Order_Key); // 주문서 총 금액 갱신
		cartDao.deleteCartAfterOrder(memno); // 카트 목록 삭제

		System.out.println("\n\n================");
		System.out.println("▶▶장바구니 주문완료 입니다!");
		System.out.println("=================");
	}

	// 주문서 확인하기
	public static void orderconfirm(int memNo) {
		DecimalFormat df = new DecimalFormat("###,### 원");
		OrderDao orderDao = new OrderDao();
		List<OrderVo> list = orderDao.readOrderList(memNo);
		OrderVo vo = null;

		if (list.isEmpty()) {
			System.out.println("\n\n\t[현재 주문이 없습니다]\n\n");
			return;
		}

		System.out.println("\n\n================주문 확인=================");
		System.out.println("=======================================");
		for (int i = 0; i < list.size(); i++) {
			vo = list.get(i);

			System.out.println("[" + (i + 1) + "번 주문]");
			System.out.println("주문자 : " + vo.getmName() + "\t 주문번호 : " + vo.getOrderNo());
			System.out.println("배송지 : " + vo.getAddr());
			System.out.println("총 금액 : " + df.format(vo.getPrice()));
			System.out.println("\n");
		}

		System.out.println("=======================================");
		System.out.println("=======================================");
	}
}
