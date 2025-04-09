package com.example.MonthlyMVC.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.MonthlyMVC.model.Prefecture;

/*
 * PrefectureテーブルのServiceインターフェース
 * 
 * @author 2023/10/01
 */
@Service
public interface PrefectureService {
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
  void insert(Prefecture prefecture);
  /*
   * prefectureテーブルのIDを指定して、データを削除するメソッド
   * 
   * @param id ID
   */
  void delete(Integer id);
  
}
