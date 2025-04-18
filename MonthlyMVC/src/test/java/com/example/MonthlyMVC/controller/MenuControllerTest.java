package com.example.MonthlyMVC.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.model.Prefecture;
import com.example.MonthlyMVC.repository.MansionRepository;
import com.example.MonthlyMVC.repository.PrefectureRepository;
import com.example.MonthlyMVC.service.MansionService;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MansionService mansionService;

  @MockBean
  private MansionRepository mansionRepository; // 追加: リポジトリをモック化

  @MockBean
  private PrefectureRepository prefectureRepository; // PrefectureRepositoryをモック化

  @Test
  @DisplayName("メニュー画面の表示")
  void testMenu() throws Exception {
    // Arrange & Act & Assert
    mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("menu"));
  }

  @Test
  @DisplayName("マンション一覧画面の表示")
  void testMansionList() throws Exception {
    
    //期待値の設定　mansionList.htmで必要な値だけを設定
    EntityMansion mansion1 = new EntityMansion();
    Prefecture prefecture = new Prefecture();
    prefecture.setPrefecture("東京都");

    mansion1.setId(1);
    mansion1.setName("マンションA");
    mansion1.setAddress("東京都新宿区");
    mansion1.setPrefectureId(13);
    mansion1.setMonthlyPrice(100000);
    mansion1.setBuildingDate(new Date());
    mansion1.setImagePath("/images/mansionA.jpg");
    mansion1.setDataFlag(true);
    mansion1.setPrefecture(prefecture);

    EntityMansion mansion2 = new EntityMansion();
    mansion2.setId(2);
    mansion2.setName("マンションB");
    mansion2.setAddress("大阪府大阪市");
    mansion2.setPrefectureId(27);
    mansion2.setMonthlyPrice(80000);
    mansion2.setBuildingDate(new Date());
    mansion2.setImagePath("/images/mansionB.jpg");
    mansion2.setDataFlag(false);
    mansion2.setPrefecture(prefecture);
    when(mansionService.selectAll()).thenReturn(Arrays.asList(mansion1, mansion2));

    // Act & Assert
    mockMvc.perform(get("/mansionList")).andExpect(status().isOk())
        .andExpect(view().name("mansion/mansionList"))
        .andExpect(model().attributeExists("mansionList"));

    verify(mansionService, times(1)).selectAll();
  }

  @Test
  @DisplayName("マンション情報の削除")
  void testDeleteMansion() throws Exception {
    // Arrange
    Integer id = 1;
    doNothing().when(mansionService).deleteById(id);

    // Act & Assert
    mockMvc.perform(post("/mansionDelete/{id}", id)).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/mansionList"));

    verify(mansionService, times(1)).deleteById(id);
  }
}
