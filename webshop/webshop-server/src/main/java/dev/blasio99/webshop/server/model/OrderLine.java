package dev.blasio99.webshop.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import dev.blasio99.webshop.server.enums.Payment;
import dev.blasio99.webshop.server.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table
public class OrderLine extends BaseModel{

	private ArrayList<Long> orderIdList = new ArrayList<>();
	private LocalDateTime orderDate;
	@Column(unique = true)
	private String username;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	private Payment payment;

	public ArrayList<Long> getOrderIdList() {
		return this.orderIdList;
	}

	public void setOrderIdList(ArrayList<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}
	
	public LocalDateTime getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
