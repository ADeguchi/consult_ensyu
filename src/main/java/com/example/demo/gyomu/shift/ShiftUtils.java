package com.example.demo.gyomu.shift;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ShiftUtils {

    public static List<ShiftTourokuEntity> generateBlankShift(int shainId, YearMonth ym) {
        List<ShiftTourokuEntity> list = new ArrayList<>();
        int ymInt = ym.getYear() * 100 + ym.getMonthValue();

        for (int day = 1; day <= ym.lengthOfMonth(); day++) {
            ShiftTourokuEntity e = new ShiftTourokuEntity();
            e.setShiftId(shainId * 10000 + day);
            e.setShiftYm(ymInt);
            e.setShainId(shainId);
            e.setShiftDate(ym.atDay(day));
            e.setShiftUm(false);
            e.setAmPm(null);
            list.add(e);
        }
        return list;
    }
}
