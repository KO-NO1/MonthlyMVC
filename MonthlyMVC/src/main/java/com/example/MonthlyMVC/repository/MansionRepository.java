package com.example.MonthlyMVC.repository;

import java.util.List;
import com.example.MonthlyMVC.model.Mansion;

/*
 * MansionテーブルのRepositoryインターフェース
 */
public interface MansionRepository {
  /*
   * mansionテーブルの全データを取得するメソッド
   */
  List<Mansion> selectAll();
  
  /*
   * mansionテーブルに登録するメソッド
   * @param mansion Mansion
   */
  Boolean insert(Mansion mansion);
  /*
   * mansionテーブルのIDを指定して、データを取得するメソッド
   * @param id ID
   * @return Mansion
   */
  Mansion selectByIdDetail(Integer id);
  
  /*
   * mansionテーブルのIDを指定して、データを削除するメソッド
   */
  void deleteById(Integer id); // 削除メソッドを追加
}
