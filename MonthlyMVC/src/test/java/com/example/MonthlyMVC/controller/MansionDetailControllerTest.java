package com.example.MonthlyMVC.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.exception.ResourceNotFoundException;
import com.example.MonthlyMVC.model.Prefecture;
import com.example.MonthlyMVC.repository.MansionRepository;
import com.example.MonthlyMVC.repository.PrefectureRepository;
import com.example.MonthlyMVC.service.MansionService;

@WebMvcTest(MansionDetailController.class)
class MansionDetailControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MansionService mansionService;

  @MockBean
  private MansionRepository mansionRepository; // 追加: リポジトリをモック化

  @MockBean
  private PrefectureRepository prefectureRepository; // PrefectureRepositoryをモック化

  @Test
  @DisplayName("マンション詳細画面の表示_正常系")
  void testMansionDetail_1() throws Exception {
    // Arrange
    Integer id = 1;
    Prefecture prefecture = new Prefecture();
    prefecture.setPrefecture("東京都");
    // 期待値の設定
    EntityMansion mansion = new EntityMansion();
    mansion.setId(id);
    mansion.setName("マンションA");
    mansion.setAddress("東京都新宿区");
    mansion.setMonthlyPrice(100000);
    mansion.setBuildingDate(new java.util.Date());
    mansion.setImagePath("/images/mansionA.jpg");
    mansion.setDataFlag(true);
    mansion.setPrefecture(prefecture);


    when(mansionService.selectByIdDetail(id)).thenReturn(mansion);

    // Act & Assert
    mockMvc.perform(get("/mansionDetail/{id}", id)).andExpect(status().isOk())
        .andExpect(view().name("mansion/mansionDetail"))
        .andExpect(model().attributeExists("mansionDetail"))
        .andExpect(model().attribute("mansionDetail", mansion));

    verify(mansionService, times(1)).selectByIdDetail(id);
  }

  @Test
  @DisplayName("マンション詳細画面の表示_異常系_リソースが見つからない")
  void testMansionDetail_2() throws Exception {
      // Arrange
      Integer id = 999;
      when(mansionService.selectByIdDetail(id))
          .thenReturn(null);

      // Act & Assert
      mockMvc.perform(get("/mansionDetail/{id}", id))
          .andExpect(status().isNotFound())
          .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
          .andExpect(result -> assertEquals("マンションが見つかりません: ID=" + id,
              result.getResolvedException().getMessage()));

      verify(mansionService, times(1)).selectByIdDetail(id);
  }
}
