package com.example.demo.gyomu.shift;

import java.sql.Date;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShiftTourokuRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShiftTourokuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 社員プルダウン用
    public Map<Integer, String> selectShainDb() {
        return jdbcTemplate.query("SELECT shain_id, shain_name FROM shain_m ORDER BY shain_id",
            rs -> {
                Map<Integer, String> map = new java.util.LinkedHashMap<>();
                while (rs.next()) {
                    map.put(rs.getInt("shain_id"), rs.getString("shain_name"));
                }
                return map;
            });
    }

    // シフト存在確認
    public boolean existsByShainAndMonth(int shainId, int ym) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM shift_data WHERE shain_id = ? AND shift_ym = ?",
            Integer.class, shainId, ym);
        return count != null && count > 0;
    }

    // 初期登録（削除→バルクインサート）
    public void insertShiftBatch(List<ShiftTourokuEntity> list) {
        if (list.isEmpty()) return;

        int shainId = list.get(0).getShainId();
        int ymInt = list.get(0).getShiftYm();
        jdbcTemplate.update("DELETE FROM shift_data WHERE shain_id=? AND shift_ym=?", shainId, ymInt);

        String sql = "INSERT INTO shift_data (shift_id, shift_ym, shain_id, shift_date, shift_um, ampm) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, list, list.size(), (ps, e) -> {
            ps.setInt(1, e.getShiftId());
            ps.setInt(2, e.getShiftYm());
            ps.setInt(3, e.getShainId());
            ps.setDate(4, Date.valueOf(e.getShiftDate()));
            ps.setBoolean(5, e.getShiftUm() != null ? e.getShiftUm() : false);
            ps.setString(6, e.getAmPm());
        });
    }

    // 一括更新
    public void updateShifts(List<ShiftTourokuEntity> list) {
        for (ShiftTourokuEntity s : list) {
        	if(s.isDelete()) {
        	     jdbcTemplate.update("UPDATE shift_data SET shift_um = FALSE, ampm = " + 
        	      "null WHERE shift_id = ?",s.getShiftId());
        	}else {
               //System.out.println("UPDATE shift_data SET shift_um =" + s.getShiftUm() + ", ampm =" + s.getAmPm() + " WHERE shift_id = " + s.getShiftId());
        		jdbcTemplate.update(
                    "UPDATE shift_data SET shift_um = ?, ampm = ? WHERE shift_id = ?",
                     s.getShiftUm(), s.getAmPm(), s.getShiftId());
        	}
        }
    }

    // 単件更新
    public int updateShift(ShiftTourokuEntity shift) {
        String sql = "UPDATE shift_data SET shift_um = ?, ampm = ? WHERE shift_id = ?";
        return jdbcTemplate.update(sql, shift.getShiftUm(), shift.getAmPm(), shift.getShiftId());
    }

    // 社員別・年月指定で取得
    public List<ShiftTourokuEntity> findShiftsByShainAndYm(int shainId, YearMonth ym) {
        int ymInt = ym.getYear() * 100 + ym.getMonthValue();

        String sql = "SELECT sd.*, sm.shain_name " +
                     "FROM shift_data sd JOIN shain_m sm ON sd.shain_id = sm.shain_id " +
                     "WHERE sd.shain_id = ? AND sd.shift_ym = ? ORDER BY sd.shift_date";

        return jdbcTemplate.query(sql, new Object[]{shainId, ymInt}, (rs, rowNum) -> {
            ShiftTourokuEntity entity = new ShiftTourokuEntity();
            entity.setShiftId(rs.getInt("shift_id"));
            entity.setShiftYm(rs.getInt("shift_ym"));
            entity.setShainId(rs.getInt("shain_id"));
            entity.setShiftDate(rs.getDate("shift_date").toLocalDate());
            entity.setShiftUm(rs.getBoolean("shift_um"));
            entity.setAmPm(rs.getString("ampm"));
            entity.setShainName(rs.getString("shain_name"));
            return entity;
        });
    }

    // 全社員の当月分シフトを取得（JOIN付）
    public List<ShiftTourokuEntity> findAllCurrentMonthShifts(int ym) {
        String sql = "SELECT sd.*, sm.shain_name " +
                     "FROM shift_data sd JOIN shain_m sm ON sd.shain_id = sm.shain_id " +
                     "WHERE shift_ym = ? ORDER BY shift_date, sd.shain_id";

        return jdbcTemplate.query(sql, new Object[]{ym}, (rs, rowNum) -> {
            ShiftTourokuEntity e = new ShiftTourokuEntity();
            e.setShiftId(rs.getInt("shift_id"));
            e.setShiftYm(rs.getInt("shift_ym"));
            e.setShainId(rs.getInt("shain_id"));
            e.setShiftDate(rs.getDate("shift_date").toLocalDate());
            e.setShiftUm(rs.getBoolean("shift_um"));
            e.setAmPm(rs.getString("ampm"));
            // Optional: shain_name をエンティティに追加したい場合はフィールドとゲッターセッターも追加
            return e;
        });
    }

    // シフト1件削除
    public int deleteShift(int shiftId) {
        return jdbcTemplate.update("DELETE FROM shift_data WHERE shift_id = ?", shiftId);
    }
    
    public List<ShiftTourokuEntity> findByYm(int ym) {
        String sql = "SELECT shift_id, shift_ym, shain_id, shift_date, shift_um, ampm " +
                     "FROM shift_data WHERE shift_ym = ? ORDER BY shift_date, shain_id";

        return jdbcTemplate.query(sql, new Object[]{ym}, (rs, rowNum) -> {
            ShiftTourokuEntity entity = new ShiftTourokuEntity();
            entity.setShiftId(rs.getInt("shift_id"));
            entity.setShiftYm(rs.getInt("shift_ym"));
            entity.setShainId(rs.getInt("shain_id"));
            entity.setShiftDate(rs.getDate("shift_date").toLocalDate());
            entity.setShiftUm(rs.getBoolean("shift_um"));
            entity.setAmPm(rs.getString("ampm"));
            return entity;
        });
    }
    
 // 例：202506 形式の int を受け取り全社員分シフトを返す
    public List<ShiftTourokuEntity> findAllByYm(int ymInt) {
        String sql = """
            SELECT sd.*, sm.shain_name
              FROM shift_data sd
              JOIN shain_m sm ON sd.shain_id = sm.shain_id
             WHERE sd.shift_ym = ?
             ORDER BY sd.shift_date, sd.shain_id
        """;

        return jdbcTemplate.query(sql, ps -> ps.setInt(1, ymInt), (rs, n) -> {
            ShiftTourokuEntity e = new ShiftTourokuEntity();
            e.setShiftId  (rs.getInt("shift_id"));
            e.setShiftYm  (rs.getInt("shift_ym"));
            e.setShainId  (rs.getInt("shain_id"));
            e.setShiftDate(rs.getDate("shift_date").toLocalDate());
            e.setShiftUm  (rs.getBoolean("shift_um"));
            e.setAmPm     (rs.getString("ampm"));
            e.setShainName(rs.getString("shain_name"));
            return e;
        });
    }
}
