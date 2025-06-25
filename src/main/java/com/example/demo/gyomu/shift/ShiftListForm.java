package com.example.demo.gyomu.shift;

import java.util.ArrayList;
import java.util.List;

public class ShiftListForm {
    private List<ShiftTourokuEntity> shiftList = new ArrayList<>();;
    
    public List<ShiftTourokuEntity> getShiftList() {
        return shiftList;
    }
    public void setShiftList(List<ShiftTourokuEntity> shiftList) {
        this.shiftList = shiftList;
    }
}
