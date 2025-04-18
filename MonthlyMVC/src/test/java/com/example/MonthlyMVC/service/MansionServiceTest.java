package com.example.MonthlyMVC.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.MonthlyMVC.entity.EntityMansion;

class MansionServiceTest {

  @Mock
  private MansionService mansionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  
  void testSelectAll() {
    // Arrange
    EntityMansion mansion1 = new EntityMansion();
    EntityMansion mansion2 = new EntityMansion();
    List<EntityMansion> mockMansions = Arrays.asList(mansion1, mansion2);
    when(mansionService.selectAll()).thenReturn(mockMansions);

    // Act
    List<EntityMansion> result = mansionService.selectAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(mansionService, times(1)).selectAll();
  }

  @Test
  void testInsert() {
    // Arrange
    EntityMansion mansion = new EntityMansion();
    when(mansionService.insert(mansion)).thenReturn(true);

    // Act
    Boolean result = mansionService.insert(mansion);

    // Assert
    assertTrue(result);
    verify(mansionService, times(1)).insert(mansion);
  }

  @Test
  void testSelectByIdDetail() {
    // Arrange
    Integer id = 1;
    EntityMansion mansion = new EntityMansion();
    when(mansionService.selectByIdDetail(id)).thenReturn(mansion);

    // Act
    EntityMansion result = mansionService.selectByIdDetail(id);

    // Assert
    assertNotNull(result);
    verify(mansionService, times(1)).selectByIdDetail(id);
  }

  @Test
  void testDeleteById() {
    // Arrange
    Integer id = 1;
    doNothing().when(mansionService).deleteById(id);

    // Act
    mansionService.deleteById(id);

    // Assert
    verify(mansionService, times(1)).deleteById(id);
  }
}

