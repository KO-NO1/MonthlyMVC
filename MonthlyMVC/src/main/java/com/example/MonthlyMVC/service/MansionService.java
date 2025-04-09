package com.example.MonthlyMVC.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.model.Mansion;

/*
 * MansionテーブルのServiceインターフェース
 */
@Service
public interface MansionService {
  /*
   * mansionテーブルの全データを取得するメソッド
   */
  List<Mansion> selectAll();

  /*
   * mansionテーブルに登録するメソッド
   * 
   * @param entityMansion EntityMansion
   */
  Boolean insert(EntityMansion entityMansion);

  /*
   * mansionテーブルのIDを指定して、データを取得するメソッド
   * 
   * @param id ID
   * 
   * @return EntityMansion
   */
  Mansion selectByIdDetail(Integer id);

  /*
   * mansionテーブルのIDを指定して、データを削除するメソッド
   */
  void deleteById(Integer id); // 削除メソッドを追加
}
