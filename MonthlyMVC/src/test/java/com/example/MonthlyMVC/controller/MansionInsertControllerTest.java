package com.example.MonthlyMVC.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.support.SessionStatus;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.entity.EntityPrefecture;
import com.example.MonthlyMVC.form.MansionInsertForm;
import com.example.MonthlyMVC.service.MansionService;
import com.example.MonthlyMVC.service.PrefectureService;
import com.example.MonthlyMVC.util.FileHelper;

@SpringBootTest
@AutoConfigureMockMvc
class MansionInsertControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrefectureService prefectureService;

  @MockBean
  private MansionService mansionService;

  @MockBean
  private FileHelper fileHelper; // FileHelperをモック

  @MockBean
  private SessionStatus sessionStatus;

  @Test
  @DisplayName("マンション登録画面の表示")
  void testEntry() throws Exception {
    // Arrange
    EntityPrefecture prefecture1 = new EntityPrefecture();
    prefecture1.setId(1);
    prefecture1.setPrefecture("東京都");
    prefecture1.setMaxMonthlyPrice(100000);

    EntityPrefecture prefecture2 = new EntityPrefecture();
    prefecture2.setId(2);
    prefecture2.setPrefecture("大阪府");
    prefecture2.setMaxMonthlyPrice(80000);

    List<EntityPrefecture> prefectureList = Arrays.asList(prefecture1, prefecture2);
    when(prefectureService.selectAll()).thenReturn(prefectureList);

    // Act & Assert
    mockMvc.perform(get("/mansionInsert")).andExpect(status().isOk())
        .andExpect(view().name("mansion/mansionInsert"))
        .andExpect(model().attributeExists("prefectureList"))
        .andExpect(model().attribute("prefectureList", prefectureList));
  }

  @Test
  @DisplayName("マンション登録確認画面の表示")
  void testConfirm_1() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    form.setName("マンションA");
    form.setAddress("東京都新宿区");
    form.setPrefectureId(1);
    form.setPrefecture("東京都");
    form.setMonthlyPrice(100000);
    form.setBuildingDate(new java.util.Date());
    form.setImagePath("/images/mansionA.jpg");

    EntityPrefecture prefecture = new EntityPrefecture();
    prefecture.setId(2); // MansionInsertFormと違うIDを設定
    prefecture.setPrefecture("東京都");
    prefecture.setMaxMonthlyPrice(100000);

    List<EntityPrefecture> prefectureList = Arrays.asList(prefecture);


    when(prefectureService.selectById(1)).thenReturn(prefecture);
    // Act & Assert
    mockMvc
        .perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form)
            .sessionAttr("prefectureList", prefectureList)) // セッションにprefectureListを設定
        .andExpect(view().name("mansion/mansionConfirm")) //
        .andExpect(model().attributeExists("prefectureList")); // 都道府県リストが再設定されていることを確認
  }

  @Test
  @DisplayName("マンション登録確認画面の表示_MansionInsertFormの全てのプロパティーでバリデーションエラー")
  void testConfirm_2() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    form.setName("");// 空のマンション名が1文字未満
    form.setAddress(
        "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
    form.setPrefectureId(null);
    form.setMonthlyPrice(null);
    form.setBuildingDate(null);

    EntityPrefecture prefecture = new EntityPrefecture();
    prefecture.setId(1);
    prefecture.setPrefecture("東京都");
    prefecture.setMaxMonthlyPrice(100000);

    List<EntityPrefecture> prefectureList = Arrays.asList(prefecture);

    // Act & Assert
    mockMvc
        .perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form)
            .sessionAttr("prefectureList", prefectureList)) // セッションにprefectureListを設定
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionInsert")) // エラー時はフォーム画面に戻る
        .andExpect(model().attributeHasFieldErrors("mansionInsertForm", "name", "address",
            "prefectureId", "monthlyPrice", "buildingDate")) // 各フィールドにエラーがあることを確認
        .andExpect(model().attributeExists("prefectureList")); // 都道府県リストが再設定されていることを確認 //
                                                               // 都道府県リストが再設定されていることを確認
  }

  // imageFileがnullの場合のテストはtestConfirm_1()でカバーされているため、ここではimageFileが空の画像ファイルの場合をテストします。
  @Test
  @DisplayName("マンション登録確認画面の表示_MultipartFileが空の画像ファイルの場合_noImageを登録")
  void testConfirm_3() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    form.setName("マンションA");
    form.setAddress("東京都新宿区");
    form.setPrefectureId(1);
    form.setMonthlyPrice(100000);
    form.setBuildingDate(new java.util.Date());

    MockMultipartFile emptyFile = new MockMultipartFile("imageFile", "", "image/png", new byte[0]);
    form.setImageFile(emptyFile); // ファイル名が空の画像ファイルを設定

    EntityPrefecture prefecture = new EntityPrefecture();
    prefecture.setId(1);
    prefecture.setPrefecture("東京都");
    prefecture.setMaxMonthlyPrice(150000);

    List<EntityPrefecture> prefectureList = Arrays.asList(prefecture);

    when(prefectureService.selectById(1)).thenReturn(prefecture);

    // Act & Assert
    mockMvc
        .perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form)
            .sessionAttr("prefectureList", prefectureList)) // セッションにprefectureListを設定
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionConfirm")) // 確認画面に遷移
        .andExpect(model().attribute("mansionInsertForm",
            hasProperty("imagePath", is("/image/noImage.png"))));
  }

  @Test
  @DisplayName("マンション登録確認画面の表示_画像ファイルのアップロード_正常")
  void testConfirm_4() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    form.setName("マンションA");
    form.setAddress("東京都新宿区");
    form.setPrefectureId(1);
    form.setMonthlyPrice(100000);
    form.setBuildingDate(new java.util.Date());

    // モック画像ファイルを作成
    MockMultipartFile mockFile = new MockMultipartFile("imageFile", // フィールド名
        "testImage.png", // ファイル名
        "image/png", // コンテンツタイプ
        "dummy image content".getBytes() // バイナリデータ
    );
    form.setImageFile(mockFile);

    EntityPrefecture prefecture = new EntityPrefecture();
    prefecture.setId(1);
    prefecture.setPrefecture("東京都");
    prefecture.setMaxMonthlyPrice(150000);

    List<EntityPrefecture> prefectureList = Arrays.asList(prefecture);

    when(prefectureService.selectById(1)).thenReturn(prefecture);
    doNothing().when(fileHelper).multipartToFile(mockFile); // fileHelperが正常に動作するようにモック

    // Act & Assert
    mockMvc
        .perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form)
            .sessionAttr("prefectureList", prefectureList)) // セッションにprefectureListを設定
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionConfirm")) // 確認画面に遷移
        .andExpect(model().attribute("mansionInsertForm",
            hasProperty("imagePath", is("/image/testImage.png"))));
  }

  @Test
  @DisplayName("マンション登録確認画面の表示_画像ファイルのアップロード_IOException発生")
  void testConfirm_5() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    form.setName("マンションA");
    form.setAddress("東京都新宿区");
    form.setPrefectureId(1);
    form.setMonthlyPrice(100000);
    form.setBuildingDate(new java.util.Date());

    // モック画像ファイルを作成
    MockMultipartFile mockFile = new MockMultipartFile("imageFile", // フィールド名
        "testImage.png", // ファイル名
        "image/png", // コンテンツタイプ
        "dummy image content".getBytes() // バイナリデータ
    );
    form.setImageFile(mockFile);

    EntityPrefecture prefecture = new EntityPrefecture();
    prefecture.setId(1);
    prefecture.setPrefecture("東京都");
    prefecture.setMaxMonthlyPrice(150000);

    List<EntityPrefecture> prefectureList = Arrays.asList(prefecture);

    when(prefectureService.selectById(1)).thenReturn(prefecture);
    doThrow(new IOException("File upload failed")).when(fileHelper).multipartToFile(mockFile); // 例外をスローするように設定

    // Act & Assert
    mockMvc
        .perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form)
            .sessionAttr("prefectureList", prefectureList)) // セッションにprefectureListを設定
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionInsert")) // エラー時はフォーム画面に戻る
        .andExpect(model().attributeExists("prefectureList")); // 都道府県リストが再設定されていることを確認
  }

  @Test
  @DisplayName("マンション登録確認画面の表示_バリデーションエラー_マンション価格が1未満かまたはマンション価格が都道府県の最大価格を超える")
  void testConfirm_6() throws Exception {

    for (int i = 0; i < 2; i++) {

      // Arrange
      MansionInsertForm form = new MansionInsertForm();
      if (i == 0) {
        form.setMonthlyPrice(0);// 1円未満のマンション価格
      } else {
        form.setMonthlyPrice(200000); // 都道府県の最大価格を超えるマンション価格
      }
      form.setPrefectureId(1);

      EntityPrefecture prefecture = new EntityPrefecture();
      prefecture.setMaxMonthlyPrice(100000);

      when(prefectureService.selectById(1)).thenReturn(prefecture);

      // Act & Assert
      mockMvc.perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form)) // セッションにprefectureListを設定
          .andExpect(status().isOk()).andExpect(view().name("mansion/mansionInsert")) // エラー時はフォーム画面に戻る
          .andExpect(model().attributeHasFieldErrors("mansionInsertForm", "monthlyPrice")); // 都道府県リストが再設定されていることを確認
    }
  }

  // 両方が null でない場合はほかのtestメソッドでカバーされているため、ここではテストしない
  @Test
  @DisplayName("マンション登録確認画面の表示_バリデーションエラー_prefectureIdまたはmonthlyPriceがnull")
  void testConfirm_7() throws Exception {
    // Case 1: prefectureId が null
    MansionInsertForm form1 = new MansionInsertForm();
    form1.setPrefectureId(null);
    form1.setMonthlyPrice(100000); // 有効な値
    mockMvc.perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form1))
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionInsert")); // エラー時はフォーム画面に戻る

    // Case 2: monthlyPrice が null
    MansionInsertForm form2 = new MansionInsertForm();
    form2.setPrefectureId(1); // 有効な値
    form2.setMonthlyPrice(null);
    mockMvc.perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form2))
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionInsert"));

    // Case 3: 両方が null
    MansionInsertForm form3 = new MansionInsertForm();
    form3.setPrefectureId(null);
    form3.setMonthlyPrice(null);
    mockMvc.perform(post("/mansionInsert/confirm").flashAttr("mansionInsertForm", form3))
        .andExpect(status().isOk()).andExpect(view().name("mansion/mansionInsert"));
  }

  @Test
  @DisplayName("マンション登録完了画面の表示")
  void testComplete() throws Exception {
    // Arrange
    EntityMansion mansion = new EntityMansion();
    mansion.setName("マンションA");
    mansion.setAddress("東京都新宿区");
    mansion.setPrefectureId(1);
    mansion.setMonthlyPrice(100000);
    mansion.setBuildingDate(new java.util.Date());
    mansion.setImagePath("/images/mansionA.jpg");

    MansionInsertForm form = new MansionInsertForm();
    form.setName("マンションA");
    form.setAddress("東京都新宿区");
    form.setPrefectureId(1);
    form.setMonthlyPrice(100000);
    form.setBuildingDate(new java.util.Date());
    form.setImagePath("/images/mansionA.jpg");

    // モックの動作を定義
    when(mansionService.insert(any(EntityMansion.class))).thenReturn(true);
    // Act & Assert
    mockMvc.perform(post("/mansionInsert/complete").flashAttr("mansionInsertForm", form)) // flashAttrでフォームデータを渡す
        .andExpect(status().is3xxRedirection()); // リダイレクトを確認
    // モックの呼び出しを検証
    verify(mansionService, times(1)).insert(any());
  }

  @Test
  @DisplayName("マンション登録完了画面の表示_リダイレクト")
  void testMansionComplete() throws Exception {
    // Arrange
    String name = "マンションA";

    // Act & Assert
    mockMvc.perform(get("/mansionInsert/mansionComplete").param("name", name)) // nameパラメータを設定
        .andExpect(status().isOk()) // ステータスコード200を確認
        .andExpect(view().name("mansion/mansionComplete")) // ビュー名を確認
        .andExpect(model().attribute("name", name)); // モデルにnameが設定されていることを確認
  }

  @Test
  @DisplayName("マンション登録完了画面の表示_リダイレクト_画像ファイルがnoImageの以外")
  void testEntryBack() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    // モック画像ファイルを作成
    MockMultipartFile mockFile = new MockMultipartFile("imageFile", // フィールド名
        "testImage.png", // ファイル名
        "image/png", // コンテンツタイプ
        "dummy image content".getBytes() // バイナリデータ
    );
    form.setImageFile(mockFile);
    form.setImagePath("/image/testImage.png");

    doNothing().when(fileHelper).fileDelete(mockFile); // fileHelperが正常に動作するようにモック

    // Act & Assert
    mockMvc.perform(post("/mansionInsert/complete").param("entryBack", "") // entryBackパラメータを設定
        .flashAttr("mansionInsertForm", form)).andExpect(status().is3xxRedirection()) // リダイレクトを確認
        .andExpect(redirectedUrl("/mansionInsert"));
  }

  @Test
  @DisplayName("マンション登録完了画面の表示_リダイレクト_画像ファイルがnoImageの場合")
  void testEntryBack_NoImage() throws Exception {
    // Arrange
    MansionInsertForm form = new MansionInsertForm();
    form.setImagePath("/image/noImage.png"); // noImageを設定

    // Act & Assert
    mockMvc.perform(post("/mansionInsert/complete").param("entryBack", "") // entryBackパラメータを設定
        .flashAttr("mansionInsertForm", form)) // フォームデータを渡す
        .andExpect(status().is3xxRedirection()) // リダイレクトを確認
        .andExpect(redirectedUrl("/mansionInsert")); // リダイレクト先を確認

    // fileHelper.fileDeleteは呼び出されないことを確認
    verify(fileHelper, never()).fileDelete(any());
  }


}
