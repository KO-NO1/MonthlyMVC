package com.example.MonthlyMVC.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.MonthlyMVC.entity.EntityPrefecture;

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
  EntityPrefecture selectById(Integer id);
  /*
   * prefectureテーブルの全データを取得するメソッド
   * 
   * @return List<Prefecture>
   */
  List<EntityPrefecture> selectAll();
  /*
   * prefectureテーブルに登録するメソッド
   * 
   * @param prefecture Prefecture
   */
  Boolean insert(EntityPrefecture entityPrefecture);
  /*
   * prefectureテーブルのIDを指定して、データを削除するメソッド
   * 
   * @param id ID
   */
  Boolean delete(Integer id);
  
}
