package com.example.MonthlyMVC.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.MonthlyMVC.entity.EntityPrefecture;
import com.example.MonthlyMVC.model.Prefecture;
import com.example.MonthlyMVC.repository.PrefectureRepository;

@Service
public class PrefectureServiceImpl implements PrefectureService {

  @Autowired
  PrefectureRepository prefectureRepository;

  @Override
  public EntityPrefecture selectById(Integer id) {

    Prefecture prefecture = prefectureRepository.selectById(id);
    EntityPrefecture entityPrefecture = new EntityPrefecture();
    BeanUtils.copyProperties(prefecture, entityPrefecture);

    return entityPrefecture;

  }

  @Override
  public List<EntityPrefecture> selectAll() {

    List<Prefecture> prefectureList = prefectureRepository.selectAll();
    List<EntityPrefecture> entityPrefectureList = new ArrayList<>();
    EntityPrefecture entityPrefecture = new EntityPrefecture();

    // List<Prefecture>を List<EntityPrefecture>に変換
    for (Prefecture prefecture : prefectureList) {
      entityPrefecture = new EntityPrefecture();
      BeanUtils.copyProperties(prefecture, entityPrefecture); // プロパティを一括コピー
      entityPrefectureList.add(entityPrefecture);
    }
    return entityPrefectureList;
  }

  @Override
  public Boolean insert(EntityPrefecture entityPrefecture) {

    Prefecture prefecture = new Prefecture();
    BeanUtils.copyProperties(entityPrefecture, prefecture);
    return prefectureRepository.insert(prefecture);
  }

  @Override
  public Boolean delete(Integer id) {
    return prefectureRepository.delete(id);
  }

}
