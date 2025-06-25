package com.example.demo.gyomu.kintaiTouroku;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KintaiTourokuController {
	private final KintaiTourokuRepository repository;
	
	public KintaiTourokuController(KintaiTourokuRepository repository) {
		this.repository = repository;
	}
	
    @GetMapping("/kintaiTouroku")
    public String showForm(Model model) {
        model.addAttribute("form", new KintaiTourokuForm());
        Map<Integer, String> list = repository.selectShainDb();
        
        model.addAttribute("shainList", list);
        
        return "kintai_touroku";
    }

    @PostMapping("/kintaiTouroku")
    public String submit(@ModelAttribute KintaiTourokuForm form) {
    	repository.insertKintaiTourokuDb(form.getShainId(), form.getArrivalDateTime(), form.getFinishDateTime());
        return "redirect:/kintaiTouroku"; // 成功後は同じフォームに戻る、または完了ページへ遷移
    }
    
    @GetMapping("/kintaiList")
    public String showKintaiList(@ModelAttribute("form") KintaiListForm form, Model model) {
        Map<Integer, String> shainList = repository.selectShainDb(); // Mapで返す前提
        model.addAttribute("shainList", shainList);

        List<KintaiTourokuForm> result = null;
        if (form.getShainId() != null || form.getFrom() != null || form.getTo() != null) {
        	result = repository.selectKintaiFiltered(form.getShainId(), form.getFrom(), form.getTo());
        }
        model.addAttribute("kintaiList", result);
        model.addAttribute("form", form);
        
        return "kintai_list";
    }
	
}
