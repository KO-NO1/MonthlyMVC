package com.example.MonthlyMVC.util;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class FileHelperTest {

  private FileHelper fileHelper;

  private final String testDir = "test-images"; // テスト用のディレクトリ
  private final String testFileName = "testImage.jpg";

  @BeforeEach
  void setUp() {
    // テスト用ディレクトリを注入
    fileHelper = new FileHelper(testDir);

    // テスト用ディレクトリを作成
    new File(testDir).mkdirs();
  }

  @AfterEach
  void tearDown() {
    // テスト用ディレクトリを削除
    File dir = new File(testDir);
    for (File file : dir.listFiles()) {
      file.delete();
    }
    dir.delete();
  }

  @Test
  @DisplayName("multipartToFileが正常にファイルを保存することを確認")
  void testMultipartToFile() throws IOException {
    // Arrange
    MockMultipartFile mockFile = new MockMultipartFile("imageFile", testFileName, "image/jpeg",
        "dummy image content".getBytes());

    // Act
    fileHelper.multipartToFile(mockFile);

    // Assert
    File savedFile = new File(testDir, testFileName);
    assertTrue(savedFile.exists(), "ファイルが保存されていることを確認");
  }

  @Test
  @DisplayName("fileDeleteが正常にファイルを削除することを確認")
  void testFileDelete() throws IOException {
    // Arrange
    File fileToDelete = new File(testDir, testFileName);
    fileToDelete.createNewFile(); // テスト用のファイルを作成

    // Act
    fileHelper.fileDelete(new MockMultipartFile("imageFile", testFileName, "image/jpeg",
        "dummy image content".getBytes()));

    // Assert
    assertTrue(!fileToDelete.exists(), "ファイルが削除されていることを確認");
  }

}
