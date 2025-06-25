package com.example.demo.gyomu.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public OrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 支店プルダウン用
	public Map<Integer, String> selectShitenDb() {
		return jdbcTemplate.query("SELECT shiten_id, shiten_name FROM shiten_m WHERE shiten_id <> 0 ORDER BY shiten_id",
				rs -> {
					Map<Integer, String> map = new java.util.LinkedHashMap<>();
					while (rs.next()) {
						map.put(rs.getInt("shiten_id"), rs.getString("shiten_name"));
					}
					return map;
				});
	}

	//材料プルダウン用
	public Map<Integer, String> selectZairyouDb() {
		return jdbcTemplate.query("SELECT zairyou_id, zairyou_name FROM zairyou_m ORDER BY zairyou_id",
				rs -> {
					Map<Integer, String> map = new java.util.LinkedHashMap<>();
					while (rs.next()) {
						map.put(rs.getInt("zairyou_id"), rs.getString("zairyou_name"));
					}
					return map;
				});
	}

	public List<OrderEntity> findOrderByShitenAndYm(int shitenId, LocalDate orderYmd) {
		String sql = "SELECT * "
				+ "FROM order_data od JOIN zairyou_m zm ON od.zairyou_id = zm.zairyou_id "
				+ "WHERE od.order_shiten_id = ? AND od.order_date = ? ORDER BY od.order_date";
		return jdbcTemplate.query(sql, new Object[] { shitenId, orderYmd }, (rs, rowNum) -> {
			OrderEntity entity = new OrderEntity();
			entity.setOrderId(rs.getInt("order_id"));
			entity.setOrderYmd(rs.getDate("order_date").toLocalDate());
			entity.setZairyouId(rs.getInt("zairyou_id"));
			entity.setZairyouNm(rs.getString("zairyou_name"));
			entity.setOrderAmount(rs.getInt("order_amount"));
			entity.setOrderShitenId(rs.getInt("order_shiten_id"));
			return entity;
		});
	}

	// INSERT
	public void insertOrder(OrderEntity order) {
		String sql = "INSERT INTO order_data (order_date, zairyou_id, order_amount, order_shiten_id) " +
				"VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				order.getOrderYmd(),
				order.getZairyouId(),
				order.getOrderAmount(),
				order.getOrderShitenId());
	}

	// UPDATE
	public void updateOrder(OrderEntity order) {
		String sql = "UPDATE order_data SET order_date = ?, zairyou_id = ?, order_amount = ?, order_shiten_id = ? " +
				"WHERE order_id = ?";
		jdbcTemplate.update(sql,
				order.getOrderYmd(),
				order.getZairyouId(),
				order.getOrderAmount(),
				order.getOrderShitenId(),
				order.getOrderId());
	}

	public void deleteById(int orderId) {
		jdbcTemplate.update("DELETE FROM order_data WHERE order_id = ?", orderId);
	}

}
