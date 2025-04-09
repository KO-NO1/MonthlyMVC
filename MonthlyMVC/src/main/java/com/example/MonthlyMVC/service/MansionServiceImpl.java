package com.example.MonthlyMVC.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.MonthlyMVC.entity.EntityMansion;
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
  public List<Mansion> selectAll() {
    return mansionRepository.selectAll();
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
