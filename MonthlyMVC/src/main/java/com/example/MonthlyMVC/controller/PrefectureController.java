package com.example.MonthlyMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.MonthlyMVC.model.Prefecture;
import com.example.MonthlyMVC.service.MansionService;
import com.example.MonthlyMVC.service.PrefectureService;

@Controller
public class PrefectureController {

  @Autowired
  private PrefectureService prefectureService;

  @Autowired
  private MansionService mansionService;

  @GetMapping("/Insert")
  public String showInsertForm(Model model) {
    model.addAttribute("prefecture", new Prefecture());
    return "prefecture/prefectureInsert";
  }

  @PostMapping("/prefectureInsert")
  public String insertPrefecture(Prefecture prefecture, Model model) {
    prefectureService.insert(prefecture);
    model.addAttribute("message", "都道府県を登録しました: " + prefecture.getPrefecture());
    return "prefecture/prefectureInsert";
  }

  @GetMapping("/prefectureList")
  public String showPrefectureList(Model model) {
    model.addAttribute("prefectureList", prefectureService.selectAll());
    return "prefecture/prefectureList";
  }

  @PostMapping("/prefectureDelete/{id}")
  public String deletePrefecture(@PathVariable Integer id) {
    
    prefectureService.delete(id);
    return "redirect:/prefectureList";
  }
}
