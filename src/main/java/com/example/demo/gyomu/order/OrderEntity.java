package com.example.demo.gyomu.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderEntity {
	private int orderId;
	private LocalDate orderYmd;
	private Integer zairyouId;
	private String zairyouNm;
	private Integer orderAmount;
	private int orderShitenId;
	private List<OrderEntity> orderList = new ArrayList<>();

	public OrderEntity() {}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderYmd() {
		return orderYmd;
	}

	public void setOrderYmd(LocalDate orderYmd) {
		this.orderYmd = orderYmd;
	}

	public Integer getZairyouId() {
		return zairyouId;
	}

	public void setZairyouId(Integer zairyouId) {
		this.zairyouId = zairyouId;
	}

	public String getZairyouNm() {
		return zairyouNm;
	}

	public void setZairyouNm(String zairyouNm) {
		this.zairyouNm = zairyouNm;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public int getOrderShitenId() {
		return orderShitenId;
	}

	public void setOrderShitenId(int orderShitenId) {
		this.orderShitenId = orderShitenId;
	}
	
    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }
}

