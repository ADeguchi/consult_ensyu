package com.example.demo.gyomu.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
	private final OrderRepository repository;

	public OrderController(OrderRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/order/search")
	public String searchOrder(@RequestParam(name = "shitenId", required = false) Integer shitenId,
			@RequestParam(name = "orderYmd", required = false) LocalDate orderYmd,
			Model model) {

		model.addAttribute("shitenList", repository.selectShitenDb());
		model.addAttribute("zairyouList", repository.selectZairyouDb());
		model.addAttribute("shitenId", shitenId);
		model.addAttribute("orderYmd", orderYmd);
		List<OrderEntity> orders = List.of();

		if (shitenId != null && orderYmd != null) {
			orders = repository.findOrderByShitenAndYm(shitenId, orderYmd);
			if (orders.isEmpty()) {
				model.addAttribute("message", "該当するデータは存在しません。");
			}
		}

		model.addAttribute("orderList", orders);

		OrderEntity dummyEntity = new OrderEntity();
		dummyEntity.setOrderList(orders); // null回避 + 再編集用
		model.addAttribute("entity", dummyEntity);

		return "order_list";
	}

	@PostMapping("/order/update")
	public String updateOrder(@ModelAttribute OrderEntity entity,
			@RequestParam("shitenId") Integer shitenId,
			@RequestParam("orderYmd") LocalDate orderYmd,
			Model model) {

		// 空行だけのエントリを除外（手動でフィルター）
		List<OrderEntity> validList = entity.getOrderList().stream()
				.filter(order -> order.getZairyouId() != null && order.getZairyouId() != 0
						&& order.getOrderAmount() != null && order.getOrderAmount() != 0)
				.toList(); // Java 17以上（または .collect(Collectors.toList())）

		for (OrderEntity order : validList) {
			order.setOrderYmd(orderYmd);
			order.setOrderShitenId(shitenId);
			if (order.getOrderId() == 0) {
				repository.insertOrder(order);
			} else {
				repository.updateOrder(order);
			}
		}

		return "redirect:/order/search?shitenId=" + shitenId + "&orderYmd=" + orderYmd;
	}

	@PostMapping("/order/delete")
	public String deleteOrder(@RequestParam("orderId") int orderId) {
		repository.deleteById(orderId);
		return "redirect:/order/search";
	}

}
