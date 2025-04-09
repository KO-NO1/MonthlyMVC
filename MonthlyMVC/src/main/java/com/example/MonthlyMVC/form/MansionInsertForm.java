package com.example.MonthlyMVC.form;

import java.io.Serializable;
import java.util.Date;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class MansionInsertForm implements Serializable {

  @NotNull(message = "{name.NotNull}")
  @Size(min = 1, max = 100, message = "{name.Size}")
  private String name;

  @NotNull(message = "{address.NotNull}")
  @Size(min = 1, max = 100, message = "{address.Size}")
  private String address;

  @NotNull(message = "{prefectureId.NotNull}")
  private Integer prefectureId;

  @NotNull(message = "{prefecture.NotNull}")
  private String prefecture;

  @NotNull(message = "{monthlyPrice.NotNull}")
  private Integer monthlyPrice;

  @NotNull(message = "{buildingDate.NotNull}")
  private Date buildingDate;

  // ファイルアップロード用
  // MultipartFileはSerializableが不可のため、MansionInsertForm全体をセッション保存するとエラー。
  private MultipartFile imageFile;
  private String imagePath;
  private Boolean dataFlag;
}
