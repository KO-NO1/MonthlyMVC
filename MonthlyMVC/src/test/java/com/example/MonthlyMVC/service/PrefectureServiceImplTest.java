package com.example.MonthlyMVC.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.MonthlyMVC.entity.EntityPrefecture;
import com.example.MonthlyMVC.model.Prefecture;
import com.example.MonthlyMVC.repository.PrefectureRepository;

@SpringBootTest
class PrefectureServiceImplTest {

  @Autowired
  private PrefectureServiceImpl prefectureService; // テスト対象のクラス

  @MockBean
  private PrefectureRepository prefectureRepository; // モックするリポジトリ

  @Test
  @DisplayName("selectByIdが正しいEntityPrefectureを返すことを確認")
  void testSelectById() {
      // Arrange
      Integer id = 1;

      // モックの戻り値を設定
      Prefecture mockPrefecture = new Prefecture();
      mockPrefecture.setId(id);
      mockPrefecture.setPrefecture("東京都");
      mockPrefecture.setMaxMonthlyPrice(150000);

      when(prefectureRepository.selectById(id)).thenReturn(mockPrefecture);

      // Act
      EntityPrefecture result = prefectureService.selectById(id);

      // Assert
      assertEquals(id, result.getId());
      assertEquals("東京都", result.getPrefecture());
      assertEquals(150000, result.getMaxMonthlyPrice());
  }
  
  @Test
  @DisplayName("selectAllが正しいList<EntityPrefecture>を返すことを確認")
  void testSelectAll() {
      // Arrange
      Prefecture prefecture1 = new Prefecture();
      prefecture1.setId(1);
      prefecture1.setPrefecture("東京都");
      prefecture1.setMaxMonthlyPrice(150000);

      Prefecture prefecture2 = new Prefecture();
      prefecture2.setId(2);
      prefecture2.setPrefecture("大阪府");
      prefecture2.setMaxMonthlyPrice(120000);

      List<Prefecture> mockPrefectureList = Arrays.asList(prefecture1, prefecture2);

      // モックの戻り値を設定
      when(prefectureRepository.selectAll()).thenReturn(mockPrefectureList);

      // Act
      List<EntityPrefecture> result = prefectureService.selectAll();

      // Assert
      assertEquals(2, result.size());
      assertEquals(1, result.get(0).getId());
      assertEquals("東京都", result.get(0).getPrefecture());
      assertEquals(150000, result.get(0).getMaxMonthlyPrice());
      assertEquals(2, result.get(1).getId());
      assertEquals("大阪府", result.get(1).getPrefecture());
      assertEquals(120000, result.get(1).getMaxMonthlyPrice());
  }
  
  @Test
  @DisplayName("insertが正しく動作することを確認")
  void testInsert() {
      // Arrange
      EntityPrefecture entityPrefecture = new EntityPrefecture();
      entityPrefecture.setId(1);
      entityPrefecture.setPrefecture("東京都");
      entityPrefecture.setMaxMonthlyPrice(150000);

      // モックの戻り値を設定
      when(prefectureRepository.insert(any(Prefecture.class))).thenReturn(true);

      // Act
      Boolean result = prefectureService.insert(entityPrefecture);

      // Assert
      assertTrue(result);
  }
  
  @Test
  @DisplayName("deleteが正しく動作することを確認")
  void testDelete() {
      // Arrange
      Integer id = 1;

      // モックの戻り値を設定
      when(prefectureRepository.delete(id)).thenReturn(true);

      // Act
      Boolean result = prefectureService.delete(id);

      // Assert
      assertTrue(result);
  }
  

}
