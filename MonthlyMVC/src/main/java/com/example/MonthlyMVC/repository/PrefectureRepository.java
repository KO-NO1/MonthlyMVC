package com.example.MonthlyMVC.repository;

import java.util.List;
import com.example.MonthlyMVC.model.Prefecture;

/*
 * PrefectureテーブルのRepositoryインターフェース
 */ 
public interface PrefectureRepository {
  /*
   * prefectureテーブルのIDを指定して、データを取得するメソッド
   * 
   * @param id ID
   * 
   * @return Prefecture
   */
  Prefecture selectById(Integer id);
  /*
   * prefectureテーブルの全データを取得するメソッド
   * 
   * @return List<Prefecture>
   */
  List<Prefecture> selectAll();
  /*
   * prefectureテーブルに登録するメソッド
   * 
   * @param prefecture Prefecture
   */
  Boolean insert(Prefecture prefecture);

  /* 
   *  prefectureテーブルのIDを指定して、データを削除するメソッド
   */
  Boolean delete(Integer id);
}
