package com.example.demo.gyomu.shift;

import java.time.LocalDate;

public class ShiftTourokuEntity {
    private int shiftId;
    private int shiftYm;
    private int shainId;
    private LocalDate shiftDate;
    private Boolean shiftUm;      // 出勤可否（true: 出勤可）
    private String amPm;          // 午前・午後・終日（1: 午前, 2: 午後, 3: 終日）
    private boolean delete;       // 削除チェック用
    private String shainName;     // 表示用社員名（全社員表示用）

    public ShiftTourokuEntity() {}

    // shiftId
    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    // shiftYm
    public int getShiftYm() {
        return shiftYm;
    }

    public void setShiftYm(int shiftYm) {
        this.shiftYm = shiftYm;
    }

    // shainId
    public int getShainId() {
        return shainId;
    }

    public void setShainId(int shainId) {
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

    // delete
    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    // shainName
    public String getShainName() {
        return shainName;
    }

    public void setShainName(String shainName) {
        this.shainName = shainName;
    }
}
