package com.example.MonthlyMVC.entity;

import java.io.Serializable;
import java.util.Date;
import com.example.MonthlyMVC.model.Prefecture;
import lombok.Data;

/*
 * MansionテーブルのEntityクラス
 */
@Data
public class EntityMansion implements Serializable {
  private Integer id; // ID
  private String name; // 名前
  private String address; // 住所
  private Integer prefectureId; 
  private Integer monthlyPrice; // 月額料金
  private Date buildingDate; // 建築日
  private String imagePath; // 画像パス
  private Boolean dataFlag; // データフラグF
  private Prefecture prefecture;// 都道府県情報(Prefectureテーブル)クラスの値をネスト
}
