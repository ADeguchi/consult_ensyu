package com.example.demo.gyomu.uriage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UriageRepository {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UriageRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<UriageEntity> findUriageDb(YearMonth ym, Integer shitenId) {
		
		String sql = "";
		LocalDate startDate = ym.atDay(1);
		LocalDate endDate = ym.plusMonths(1).atDay(1);
		
		if(shitenId == 0) {
			sql = "SELECT uriage_ymd, SUM(uriage_gaku) AS uriage_gaku"
		            +" FROM uriage_data WHERE uriage_ymd >= ? AND uriage_ymd < ? "
					+ "GROUP BY uriage_ymd ORDER BY uriage_ymd";
			return jdbcTemplate.query(sql, new Object[] {startDate, endDate}, (rs, rowNum) -> {
				UriageEntity e = new UriageEntity();
				//e.setUriageId(rs.getInt("uriage_id"));
				e.setUriageYmd(rs.getDate("uriage_ymd").toLocalDate());
				e.setUriageGaku(rs.getInt("uriage_gaku"));
				return e;
			});
		}else {
			sql = "SELECT * FROM uriage_data WHERE uriage_ymd >= ? AND uriage_ymd < ? AND uriage_shiten_id = ? ORDER BY uriage_ymd";			
			return jdbcTemplate.query(sql, new Object[] {startDate, endDate,shitenId}, (rs, rowNum) -> {
				UriageEntity e = new UriageEntity();
				e.setUriageId(rs.getInt("uriage_id"));
				e.setUriageYmd(rs.getDate("uriage_ymd").toLocalDate());
				e.setUriageGaku(rs.getInt("uriage_gaku"));
				return e;
			});
		}
	}
	
	public Integer findUriageSum(YearMonth ym, Integer shitenId) {
		String sql = "";
		LocalDate startDate = ym.atDay(1);
		LocalDate endDate = ym.plusMonths(1).atDay(1);
		Integer result = 0;
		if (shitenId == 0) {
			sql = "SELECT SUM(uriage_gaku) FROM uriage_data WHERE uriage_ymd >= ? AND uriage_ymd < ?";
			result = jdbcTemplate.queryForObject(sql, Integer.class, startDate, endDate);
		}else {
			sql = "SELECT SUM(uriage_gaku) FROM uriage_data WHERE uriage_ymd >= ? AND uriage_ymd < ? AND uriage_shiten_id = ?";
			result = jdbcTemplate.queryForObject(sql, Integer.class, startDate, endDate,shitenId);
		}
		
		if (result != null) {
			return result;
		}else {
			return 0;
		}
	}
	
    // 支店プルダウン用
    public Map<Integer, String> selectShitenDb() {
        return jdbcTemplate.query("SELECT shiten_id, shiten_name FROM shiten_m ORDER BY shiten_id",
            rs -> {
                Map<Integer, String> map = new java.util.LinkedHashMap<>();
                while (rs.next()) {
                    map.put(rs.getInt("shiten_id"), rs.getString("shiten_name"));
                }
                return map;
            });
    }
	
}
