package com.example.MonthlyMVC.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.MonthlyMVC.entity.EntityMansion;

/*
 * MansionテーブルのServiceインターフェース
 */
@Service
public interface MansionService {
  /*
   * mansionテーブルの全データを取得するメソッド
   */
  List<EntityMansion> selectAll();

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
  EntityMansion selectByIdDetail(Integer id);

  /*
   * mansionテーブルのIDを指定して、データを削除するメソッド
   */
  void deleteById(Integer id); // 削除メソッドを追加
}
