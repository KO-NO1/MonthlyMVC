package com.example.MonthlyMVC.entity;

import lombok.Data;

@Data
public class EntityPrefecture {
  private Integer id; // 都道府県ID
  private String prefecture; // 都道府県名
  private Integer maxMonthlyPrice; // 最大月額料金
  private Boolean dataFlag; // データフラグ
}
