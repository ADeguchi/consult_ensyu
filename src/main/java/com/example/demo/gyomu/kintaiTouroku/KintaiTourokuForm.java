package com.example.demo.gyomu.kintaiTouroku;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class KintaiTourokuForm {
	private Integer shainId; //社員ID
	
	private String shainNm;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private 	LocalDateTime arrivalDateTime;//出勤日時
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime finishDateTime;//退勤日時
	
	public KintaiTourokuForm() {
	}
	
	//社員ID
	public Integer getShainId() {
		return shainId;
	}
	public void setShainId(Integer shainId) {
		this.shainId = shainId;
	}
	
	//社員名
	public String getShainNm() {
		return shainNm;
	}
	public void setShainNm(String shainNm) {
		this.shainNm = shainNm;
	}	
	
	//出勤日時
	public LocalDateTime getArrivalDateTime() {
	    return arrivalDateTime;
	}
	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
	    this.arrivalDateTime = arrivalDateTime;
	}

	//退勤日時
	public LocalDateTime getFinishDateTime() {
	    return finishDateTime;
	}
	public void setFinishDateTime(LocalDateTime finishDateTime) {
	    this.finishDateTime = finishDateTime;
	}

}
