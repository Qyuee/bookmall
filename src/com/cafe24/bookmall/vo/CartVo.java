package com.cafe24.bookmall.vo;

public class CartVo {
	private String CartNo;
	private int qty;
	private int price;
	private int memNo;
	private int bookNo;
	
	public String getCartNo() {
		return CartNo;
	}
	public void setCartNo(String cartNo) {
		CartNo = cartNo;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public int getBookNo() {
		return bookNo;
	}
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	
	
}
