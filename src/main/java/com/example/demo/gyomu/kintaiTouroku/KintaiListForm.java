package com.example.demo.gyomu.kintaiTouroku;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class KintaiListForm {
    private Integer shainId;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;

    // ゲッター・セッター
    public Integer getShainId() {
        return shainId;
    }
    public void setShainId(Integer shainId) {
        this.shainId = shainId;
    }
    public LocalDate getFrom() {
        return from;
    }
    public void setFrom(LocalDate from) {
        this.from = from;
    }
    public LocalDate getTo() {
        return to;
    }
    public void setTo(LocalDate to) {
        this.to = to;
    }
}
