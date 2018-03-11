package com.cafe24.bookmall.vo;

public class OrderVo {
	private String orderNo;
	private int price;
	private String addr;
	private int memNo;
	
	//m.name, o.orderno, o.addr, b.title, ob.qty, ob.price, (ob.qty*ob.price) as 'total_price'
	private String mName;
	
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	
	
}
