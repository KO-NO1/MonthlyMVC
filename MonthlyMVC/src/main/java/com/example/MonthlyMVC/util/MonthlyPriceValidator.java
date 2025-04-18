package com.example.MonthlyMVC.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.example.MonthlyMVC.entity.EntityPrefecture;
import com.example.MonthlyMVC.form.MansionInsertForm;
import com.example.MonthlyMVC.service.PrefectureService;

@Component
public class MonthlyPriceValidator implements Validator {
  @Autowired
  PrefectureService prefectureService;

  @Override

  public boolean supports(Class<?> clazz) {
    return MansionInsertForm.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MansionInsertForm form = (MansionInsertForm) target;
    EntityPrefecture prefecture = null;
    if (form.getPrefectureId() != null && form.getMonthlyPrice() != null) {
      // 都道府県名と最高金額を取得
      prefecture = prefectureService.selectById(form.getPrefectureId());
      // 最高金額が1以上かつ都道府県の最高金額以下であることを確認
      if (form.getMonthlyPrice() < 1 || form.getMonthlyPrice() > prefecture.getMaxMonthlyPrice()) {
        // messages.propertiesのキーを指定
        errors.rejectValue("monthlyPrice", "monthlyPrice.message");
      }
    }
  }
}

