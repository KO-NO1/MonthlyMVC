package com.example.MonthlyMVC.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.MonthlyMVC.entity.EntityPrefecture;
import com.example.MonthlyMVC.service.PrefectureService;

@SpringBootTest
@AutoConfigureMockMvc
class PrefectureControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrefectureService prefectureService;

  @Test
  @DisplayName("都道府県登録画面の表示")
  void testShowInsertForm() throws Exception {

    mockMvc.perform(get("/Insert")).andExpect(status().isOk()) // ステータスコード200を確認
        .andExpect(view().name("prefecture/prefectureInsert")) // ビュー名を確認
        .andExpect(model().attributeExists("prefecture"));
  }


  @Test
  @DisplayName("都道府県登録")
  void testInsertPrefecture() throws Exception {

    // モックの動作を定義
    when(prefectureService.insert(any(EntityPrefecture.class))).thenReturn(true);

    // Act & Assert
    mockMvc.perform(post("/prefectureInsert").param("prefecture", "東京都")) // フォームデータを送信
        .andExpect(status().isOk()) // ステータスコード200を確認
        .andExpect(view().name("prefecture/prefectureInsert")) // ビュー名を確認
        .andExpect(model().attributeExists("message")) // モデルに"message"属性が存在することを確認
        .andExpect(model().attribute("message", "都道府県を登録しました: 東京都")); // messageの内容を確
  }

  @Test
  @DisplayName("都道府県リストの表示")
  void testShowPrefectureList() throws Exception {
    // Arrange
    EntityPrefecture prefecture1 = new EntityPrefecture();
    prefecture1.setId(1);
    prefecture1.setPrefecture("東京都");

    EntityPrefecture prefecture2 = new EntityPrefecture();
    prefecture2.setId(2);
    prefecture2.setPrefecture("大阪府");

    when(prefectureService.selectAll()).thenReturn(Arrays.asList(prefecture1, prefecture2));

    // Act & Assert
    mockMvc.perform(get("/prefectureList")).andExpect(status().isOk()) // ステータスコード200を確認
        .andExpect(view().name("prefecture/prefectureList")) // ビュー名を確認
        .andExpect(model().attributeExists("prefectureList")) // モデルに"prefectureList"属性が存在することを確認
        .andExpect(model().attribute("prefectureList", Arrays.asList(prefecture1, prefecture2))); // prefectureListの内容を確認

    // Verify
    verify(prefectureService, times(1)).selectAll(); // selectAllが1回呼び出されていることを確認
  }

  @Test
  @DisplayName("都道府県リストの表示")
  void testDeletePrefecture() throws Exception {

    Integer prefectureId= 1;
    // Arrange
    when(prefectureService.delete(prefectureId)).thenReturn(true);

    // Act & Assert
    mockMvc.perform(post("/prefectureDelete/{id}", prefectureId)) // フォームデータを送信
        .andExpect(status().is3xxRedirection()) // ステータスコード302を確認
        .andExpect(redirectedUrl("/prefectureList")); // リダイレクト先を確認
  }
}
