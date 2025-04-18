package com.example.MonthlyMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.exception.ResourceNotFoundException;
import com.example.MonthlyMVC.service.MansionService;

@Controller
public class MansionDetailController {
  @Autowired
  MansionService mansionService;

  // マンション詳細画面
  @GetMapping("/mansionDetail/{id}")
  public String mansionDetail(@PathVariable("id") Integer id, Model model) {

    EntityMansion mansion = mansionService.selectByIdDetail(id);
    
    if (mansion == null) {
      throw new ResourceNotFoundException("マンションが見つかりません: ID=" + id);
    }
    
    model.addAttribute("mansionDetail", mansion);
    return "mansion/mansionDetail";
  }
}
