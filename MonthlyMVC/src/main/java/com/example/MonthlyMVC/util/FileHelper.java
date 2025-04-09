package com.example.MonthlyMVC.util;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// 指定の場所にファイルを保存
@Component
public class FileHelper {
  // ファイルアップロード
  public void multipartToFile(MultipartFile fieldName) throws IOException {
    File convFile = new File(ImageEnum.IMAGE_DIR.getName(), fieldName.getOriginalFilename());
    System.out.println("Image Directory: " + ImageEnum.IMAGE_DIR.getName());
    fieldName.transferTo(convFile);
  }

  // ファイル削除
  public void fileDelete(MultipartFile imageFile) {
    File file = new File(ImageEnum.IMAGE_DIR.getName(), imageFile.getOriginalFilename());
    file.delete();
  }
}
