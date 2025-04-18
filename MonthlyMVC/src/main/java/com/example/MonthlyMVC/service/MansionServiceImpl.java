package com.example.MonthlyMVC.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.exception.ResourceNotFoundException;
import com.example.MonthlyMVC.model.Mansion;
import com.example.MonthlyMVC.repository.MansionRepository;

/*
 * MansionテーブルのService実装クラス
 */
@Service
public class MansionServiceImpl implements MansionService {
  @Autowired
  MansionRepository mansionRepository;

  @Override
  @Transactional(readOnly = true)
  public List<EntityMansion> selectAll() {
    List<Mansion> mansionList = mansionRepository.selectAll();
    List<EntityMansion> entityMansionList = new ArrayList<>();
    EntityMansion entityMansion = new EntityMansion();

    // List<Mansion>を List<EntityMansion>に変換
    for (Mansion mansion : mansionList) {
      entityMansion = new EntityMansion();
      BeanUtils.copyProperties(mansion, entityMansion); // プロパティを一括コピー
      entityMansionList.add(entityMansion);
    }
    return entityMansionList;
  }

  @Override
  public Boolean insert(EntityMansion entityMansion) {
    Mansion mansion = new Mansion();
    BeanUtils.copyProperties(entityMansion, mansion);

    return mansionRepository.insert(mansion);
  }

  @Override
  public EntityMansion selectByIdDetail(Integer id) {

    // IDを指定してデータを取得
    Mansion mansion = mansionRepository.selectByIdDetail(id);
    if (mansion == null) {
      throw new ResourceNotFoundException("指定されたIDのマンションが見つかりません: " + id);
    }
    // EntityMansionに変換
    EntityMansion entityMansion = new EntityMansion();
    BeanUtils.copyProperties(mansion, entityMansion);
    return entityMansion;

  }

  @Override
  public void deleteById(Integer id) {
    mansionRepository.deleteById(id);
  }
}
