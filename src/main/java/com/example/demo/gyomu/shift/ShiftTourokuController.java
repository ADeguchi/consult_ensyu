package com.example.demo.gyomu.shift;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShiftTourokuController {

    private final ShiftTourokuRepository repository;

    public ShiftTourokuController(ShiftTourokuRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/shift/touroku")
    public String showShiftForm(Model model) {
        model.addAttribute("form", new ShftTourokuForm());
        model.addAttribute("shainList", repository.selectShainDb());

        // 空のshiftListForm（明細なしでもテンプレートエラーにならないように）
        model.addAttribute("shiftListForm", new ShiftListForm());

        return "shift_touroku";
    }

    @GetMapping("/shift/search")
    public String searchAndInsert(@ModelAttribute("form") ShftTourokuForm form, Model model) {
        model.addAttribute("shainList", repository.selectShainDb());

        Integer shainId = form.getShainId();
        YearMonth ym = form.getShiftYm();
        if (shainId == null || ym == null) return "shift_touroku";

        int ymInt = ym.getYear() * 100 + ym.getMonthValue();
        List<ShiftTourokuEntity> shiftList;

        if (!repository.existsByShainAndMonth(shainId, ymInt)) {
            shiftList = ShiftUtils.generateBlankShift(shainId, ym);
            repository.insertShiftBatch(shiftList);
            model.addAttribute("message", "暫定シフトを登録しました。");
        } else {
            shiftList = repository.findShiftsByShainAndYm(shainId, ym);
            model.addAttribute("message", "既に登録済みです。編集してください。");
        }

        ShiftListForm shiftListForm = new ShiftListForm();
        shiftListForm.setShiftList(shiftList);
        model.addAttribute("shiftListForm", shiftListForm);
        model.addAttribute("form", form);

        return "shift_touroku";
    }

    @PostMapping("/shift/save")
    public String saveShifts(@ModelAttribute ShiftListForm shiftListForm, RedirectAttributes redirectAttributes) {
        repository.updateShifts(shiftListForm.getShiftList());
        redirectAttributes.addFlashAttribute("message", "シフト情報を保存しました。");
        return "redirect:/shift/touroku";
    }

    @PostMapping("/shift/update")
    public String updateShiftData(@ModelAttribute("form") ShftTourokuForm form,
                                   @RequestParam("shainId") int shainId,
                                   @RequestParam("shiftYm") String shiftYmStr,
                                   @ModelAttribute("shiftListForm") ShiftListForm shiftListForm,
                                   Model model) {
        model.addAttribute("shainList", repository.selectShainDb());
        model.addAttribute("form", form);

        for (ShiftTourokuEntity shift : shiftListForm.getShiftList()) {
            repository.updateShift(shift);
        }

        model.addAttribute("message", "更新が完了しました。");

        YearMonth ym = YearMonth.parse(shiftYmStr);
        List<ShiftTourokuEntity> updatedList = repository.findShiftsByShainAndYm(shainId, ym);
        ShiftListForm newForm = new ShiftListForm();
        newForm.setShiftList(updatedList);
        model.addAttribute("shiftListForm", newForm);

        return "shift_touroku";
    }
    
    @GetMapping("/shift/chousei")
    public String showShifts(@RequestParam(name = "ym", required = false) String ym,
                             Model model) {
        // 初回アクセス時はymがnull → 表示用にブランク画面
        if (ym == null || ym.isBlank()) {
            model.addAttribute("shiftListForm", new ShiftListForm());
            model.addAttribute("ym", "");
            return "shift_chousei";
        }

        // ym=2025-06 の形式で来るので変換（→ 202506）
        YearMonth yearMonth = YearMonth.parse(ym);
        int ymInt = yearMonth.getYear() * 100 + yearMonth.getMonthValue();

        List<ShiftTourokuEntity> shiftList = repository.findAllByYm(ymInt);
        ShiftListForm form = new ShiftListForm();
        form.setShiftList(shiftList);
        //System.out.println("検索件数：" + shiftList.size());

        model.addAttribute("shiftListForm", form);
        model.addAttribute("ym", ym);
        model.addAttribute("message", "シフトを取得しました");

        return "shift_chousei";
    }

    @PostMapping("/shift/chousei/save")
    public String updateAllShifts(@ModelAttribute ShiftListForm shiftListForm,
                                  @RequestParam("ym") String ym,
                                  RedirectAttributes redirectAttributes) {
        repository.updateShifts(shiftListForm.getShiftList());
        redirectAttributes.addFlashAttribute("message", "全社員のシフトを更新しました。");
        return "redirect:/shift/chousei?ym=" + ym;
    }
}
