package com.example.MonthlyMVC.util;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHelper {

    private final String imageDir;

    // デフォルトコンストラクタ（本番用）
    public FileHelper() {
        this.imageDir = ImageEnum.IMAGE_DIR.getName(); // デフォルトのディレクトリを使用
    }

    // テスト用にディレクトリを注入可能にするコンストラクタ
    public FileHelper(String imageDir) {
        this.imageDir = imageDir;
    }

    // ファイルアップロード
    public void multipartToFile(MultipartFile fieldName) throws IOException {
        File convFile = new File(imageDir, fieldName.getOriginalFilename());
        fieldName.transferTo(convFile);
    }

    // ファイル削除
    public void fileDelete(MultipartFile imageFile) {
        File file = new File(imageDir, imageFile.getOriginalFilename());
        file.delete();
    }
}