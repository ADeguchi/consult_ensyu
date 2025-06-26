package com.example.demo.gyomu.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {
	@GetMapping("/")
	public String face() {
		return "face_auth";
	}
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}
	@GetMapping("/menu2")
	public String menu2() {
		return "menu2";
	}
}
