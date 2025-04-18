package com.example.MonthlyMVC.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.service.MansionService;

/*
 * メニュー画面を表示するコントローラークラス
 */
@Controller
public class MenuController {

  @Autowired
  MansionService mansionService;

  /*
   * メニュー画面を表示するメソッド
   * 
   * @return String
   */
  @GetMapping("/")
  public String menu() {
    return "menu";
  }

  /*
   * マンション一覧画面を表示するメソッド
   * 
   * @param model Model
   * 
   * @return String
   */
  @GetMapping("/mansionList")
  public String mansionList(Model model) {

    List<EntityMansion> mansionList = mansionService.selectAll(); 
    model.addAttribute("mansionList", mansionList);
    return "mansion/mansionList";
  }

  /*
   * マンション情報を削除するメソッド
   * 
   * @param id ID
   * 
   * @return String
   */
  @PostMapping("/mansionDelete/{id}")
  public String deleteMansion(@PathVariable("id") Integer id) {
    // マンション情報を削除
    mansionService.deleteById(id);
    return "redirect:/mansionList";
  }
}
