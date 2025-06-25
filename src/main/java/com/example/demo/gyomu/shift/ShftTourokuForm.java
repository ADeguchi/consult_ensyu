package com.example.demo.gyomu.shift;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

public class ShftTourokuForm {
    private Integer shiftId;
    
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth shiftYm;
    private Integer shainId;

    @DateTimeFormat(pattern = "yyyy-MM-DD")
    private LocalDate shiftDate;

    private Boolean shiftUm;
    private String amPm;

    // コンストラクタ（空）
    public ShftTourokuForm() {}

    // shiftId
    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    // shiftYm
    public YearMonth getShiftYm() {
        return shiftYm;
    }

    public void setShiftYm(YearMonth shiftYm) {
        this.shiftYm = shiftYm;
    }

    // shainId
    public Integer getShainId() {
        return shainId;
    }

    public void setShainId(Integer shainId) {
        this.shainId = shainId;
    }

    // shiftDate
    public LocalDate getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(LocalDate shiftDate) {
        this.shiftDate = shiftDate;
    }

    // shiftUm
    public Boolean getShiftUm() {
        return shiftUm;
    }

    public void setShiftUm(Boolean shiftUm) {
        this.shiftUm = shiftUm;
    }

    // amPm
    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }
}
