package com.example.MonthlyMVC.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.exception.ResourceNotFoundException;
import com.example.MonthlyMVC.model.Mansion;
import com.example.MonthlyMVC.repository.MansionRepository;
/*
 * MansionテーブルのService実装クラスのテスト @SpringBootTestを使用する場合は、@Mock→@MockBeanと@InjectMocks→@Autowiredを使用してテストを行う
 */
class MansionServiceImplTest {

  @Mock
  private MansionRepository mansionRepository;

  @InjectMocks
  private MansionServiceImpl mansionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSelectAll() {
    // Arrange
    Mansion mansion1 = new Mansion();
    Mansion mansion2 = new Mansion();
    when(mansionRepository.selectAll()).thenReturn(Arrays.asList(mansion1, mansion2));

    // Act
    List<EntityMansion> result = mansionService.selectAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(mansionRepository, times(1)).selectAll();
  }

  @Test
  void testInsert() {
    // Arrange
    EntityMansion entityMansion = new EntityMansion();
    when(mansionRepository.insert(any(Mansion.class))).thenReturn(true);

    // Act
    Boolean result = mansionService.insert(entityMansion);

    // Assert
    assertTrue(result);
    verify(mansionRepository, times(1)).insert(any(Mansion.class));
  }

  @Test
  @DisplayName("IDを指定してデータを取得_正常系")
  void testSelectByIdDetail_1() {
    // Arrange
    Integer id = 1;
    Mansion mansion = new Mansion();
    when(mansionRepository.selectByIdDetail(id)).thenReturn(mansion);

    // Act
    EntityMansion result = mansionService.selectByIdDetail(id);

    // Assert
    assertNotNull(result);
    verify(mansionRepository, times(1)).selectByIdDetail(id);
  }

  @Test
  @DisplayName("IDを指定してデータを取得_異常系")
  void testSelectByIdDetail_2() {
    // Arrange
    Integer id = 1;
    when(mansionRepository.selectByIdDetail(id)).thenReturn(null);

    // Act & Assert
    assertThrows(ResourceNotFoundException.class, () -> mansionService.selectByIdDetail(id));
    verify(mansionRepository, times(1)).selectByIdDetail(id);
  }

  @Test
  void testDeleteById() {
    // Arrange
    Integer id = 1;
    doNothing().when(mansionRepository).deleteById(id);

    // Act
    mansionService.deleteById(id);

    // Assert
    verify(mansionRepository, times(1)).deleteById(id);
  }
}
