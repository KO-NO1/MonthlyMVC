package com.example.MonthlyMVC.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.MonthlyMVC.model.Prefecture;
import com.example.MonthlyMVC.repository.PrefectureRepository;

@Service
public class PrefectureServiceImpl implements PrefectureService {

  @Autowired
  PrefectureRepository prefectureRepository;

  @Override
  public Prefecture selectById(Integer id) {
    return prefectureRepository.selectById(id);

  }

  @Override
  public List<Prefecture> selectAll() {
    return prefectureRepository.selectAll();
  }
  
  @Override
  public void insert(Prefecture prefecture) {
      prefectureRepository.insert(prefecture);
  }
  
  @Override
  public void delete(Integer id) {
      prefectureRepository.delete(id);
  }

}
