package com.example.MonthlyMVC.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.entity.EntityPrefecture;
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
    EntityPrefecture entityPrefecture = new EntityPrefecture();
    // ラムダ式を使用してマッピング処理を記述
    return mansionRepository.selectAll().stream().map(modelMansion -> {
      EntityMansion entityMansion = new EntityMansion();
      entityMansion.setId(modelMansion.getId());
      entityMansion.setName(modelMansion.getName());
      entityMansion.setAddress(modelMansion.getAddress());
      entityMansion.setPrefectureId(modelMansion.getPrefecture().getId());
      entityMansion.setMonthlyPrice(modelMansion.getMonthlyPrice());
      entityMansion.setBuildingDate(modelMansion.getBuildingDate());
      entityMansion.setImagePath(modelMansion.getImagePath());
      entityMansion.setDataFlag(modelMansion.getDataFlag());
      entityMansion.setPrefecture(modelMansion.getPrefecture());
       
      return entityMansion;
    }).collect(Collectors.toList());
  }

  @Override
  public Boolean insert(EntityMansion entityMansion) {
    Mansion mansion = new Mansion();
    BeanUtils.copyProperties(entityMansion, mansion);

    return mansionRepository.insert(mansion);
  }

  @Override
  public Mansion selectByIdDetail(Integer id) {
    return mansionRepository.selectByIdDetail(id);
  }
  
  @Override
  public void deleteById(Integer id) {
    mansionRepository.deleteById(id);
  }
}
