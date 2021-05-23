package dev.blasio99.webshop.common.dto;

import java.util.ArrayList;

public class OrderLineDTO extends BaseDTO{
	
	private ArrayList<Long> orderIdList = new ArrayList<>();
	private String orderDate;
	private String username;
	private String status;
	private String payment;

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public ArrayList<Long> getOrderIdList() {
		return this.orderIdList;
	}

	public void setOrderIdList(ArrayList<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}
	
	public String getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayment() {
		return this.payment;
	}

}
