package com.example.MonthlyMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.MonthlyMVC.service.MansionService;

/*
 * メニュー画面を表示するコントローラークラス
 */
@Controller
public class MenuController {

    @Autowired
    MansionService mansionService;

    // http://localhost:8080/ で呼び出される
    @GetMapping("/")
    public String menu() {
        return "menu";
    }

    // http://localhost:8080/mansionList で呼び出される
    @GetMapping("/mansionList")
    public String mansionList(Model model) {
        model.addAttribute("mansionList", mansionService.selectAll());
        return "mansion/mansionList";
    }

    // マンション削除処理
    @PostMapping("/mansionDelete/{id}")
    public String deleteMansion(@PathVariable("id") Integer id) {
        // マンション情報を削除
        mansionService.deleteById(id); // サービスで削除処理を呼び出す
        return "redirect:/mansionList"; // 削除後に一覧画面にリダイレクト
    }
}