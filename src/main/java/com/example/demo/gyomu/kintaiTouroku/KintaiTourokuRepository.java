package com.example.demo.gyomu.kintaiTouroku;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KintaiTourokuRepository {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public KintaiTourokuRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Map<Integer, String> selectShainDb() {

	    String sql = "SELECT shain_id, shain_name FROM shain_m";

	    List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
	    Map<Integer,String> map = new LinkedHashMap<>();  // 順序保持

	    for (Map<String,Object> r : rows) {
	        Integer id   = ((Number) r.get("shain_id")).intValue(); // ← Long 対策
	        String  name = (String)  r.get("shain_name");
	        map.put(id, name);
	    }
	    return map;
	}
	
	public void insertKintaiTourokuDb(int shainId, LocalDateTime arrival, LocalDateTime finish) {
		String sql = "INSERT INTO kintai_data(shain_id, arrival_date_time, finish_date_time) VALUES (?,?,?)";
		jdbcTemplate.update(sql, shainId, arrival, finish);
	}
	
	public List<KintaiTourokuForm> selectKintaiFiltered(Integer shainId, LocalDate from, LocalDate to) {
	    StringBuilder sql = new StringBuilder("SELECT kd.*, sm.shain_name FROM kintai_data kd JOIN shain_m sm ON kd.shain_id = sm.shain_id WHERE 1=1");
	    List<Object> params = new ArrayList<>();

	    if (shainId != null) {
	        sql.append(" AND kd.shain_id = ?");
	        params.add(shainId);
	    }
	    if (from != null) {
	        sql.append(" AND kd.arrival_date_time >= ?");
	        params.add(from.atStartOfDay());
	    }
	    if (to != null) {
	        sql.append(" AND kd.arrival_date_time <= ?");
	        params.add(to.atTime(23, 59, 59));
	    }

	    return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
	        KintaiTourokuForm form = new KintaiTourokuForm();
	        form.setShainId(rs.getInt("shain_id"));
	        form.setShainNm(rs.getString("shain_name"));
	        form.setArrivalDateTime(rs.getTimestamp("arrival_date_time").toLocalDateTime());
	        form.setFinishDateTime(rs.getTimestamp("finish_date_time").toLocalDateTime());
	        return form;
	    });
	}
	
}
