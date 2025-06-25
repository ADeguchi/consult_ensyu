package com.example.demo.gyomu.uriage;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UriageController {
	private final UriageRepository repository;
	
	public UriageController(UriageRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/uriage/search")
	public String uriageData(@RequestParam(name ="uriageYm", required = false) YearMonth uriageYmd,
			@RequestParam(name= "shitenId", required = false) Integer shitenId,
			Model model) {

		model.addAttribute("shitenList", repository.selectShitenDb());
		model.addAttribute("shitenId", shitenId);
		
		if (uriageYmd == null ) {
			model.addAttribute("uriageList", new UriageEntity());
			//model.addAttribute("uriageYm", "");

			return "uriage_list";
		}
		Integer uriageSum = repository.findUriageSum(uriageYmd,shitenId);
		List<UriageEntity> uriageList = repository.findUriageDb(uriageYmd,shitenId);
		UriageEntity entity = new UriageEntity();
		entity.setUriageList(uriageList);
		
		model.addAttribute("uriageList", entity);
		model.addAttribute("uriageYm", uriageYmd);
		model.addAttribute("uriageSum", uriageSum);
        model.addAttribute("message", "売上データを取得しました");
		return "uriage_list";
	}
}
