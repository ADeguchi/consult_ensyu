package com.example.demo.gyomu.kintaiTouroku;

import java.time.LocalDateTime;

public class KintaiTourokuEntity {
	private int kintaiId; //勤怠ID
	private int shainId; //社員ID
	private String shainNm;
	private 	LocalDateTime arrivalDateTime;//出勤日時
	private LocalDateTime finishDateTime;//退勤日時
	
	public KintaiTourokuEntity() {};

	//勤怠ID
	public int getKintaiId() {
		return kintaiId;
	}
	public void setKintaiId(int kintaiId) {
		this.kintaiId = kintaiId;
	}
	
	//社員ID
	public int getShainId() {
		return shainId;
	}
	public void setShainId(int shainId) {
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
