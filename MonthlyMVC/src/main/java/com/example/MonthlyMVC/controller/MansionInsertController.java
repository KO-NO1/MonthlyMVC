package com.example.MonthlyMVC.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.MonthlyMVC.entity.EntityMansion;
import com.example.MonthlyMVC.entity.EntityPrefecture;
import com.example.MonthlyMVC.form.MansionInsertForm;
import com.example.MonthlyMVC.service.MansionService;
import com.example.MonthlyMVC.service.PrefectureService;
import com.example.MonthlyMVC.util.FileHelper;
import com.example.MonthlyMVC.util.ImageEnum;
import com.example.MonthlyMVC.util.MonthlyPriceValidator;

@Controller
@RequestMapping("mansionInsert")
@SessionAttributes({"mansionInsertForm", "prefectureList"})
public class MansionInsertController {


  @Autowired
  PrefectureService prefectureService;

  @Autowired
  FileHelper fileHelper;

  @Autowired
  MansionService mansionService;

  @Autowired
  MonthlyPriceValidator monthlyPriceValidator;

  @ModelAttribute("mansionInsertForm")
  MansionInsertForm setUpForm() {
    return new MansionInsertForm();
  }

  @GetMapping
  public String entry(Model model) {
    // 都道府県名ドロップダウンリスト作成
    List<EntityPrefecture> prefectureList = prefectureService.selectAll();

    model.addAttribute("prefectureList", prefectureList);
    return "mansion/mansionInsert";
  }

  @PostMapping("confirm")
  public String confirm(
      @SessionAttribute(value = "prefectureList",
          required = false) List<EntityPrefecture> prefectureList,
      @Validated MansionInsertForm mansionInsertForm, BindingResult bindingResult, Model model) {

    // バリデーションエラーがある場合
    if (bindingResult.hasErrors()) {
      return "mansion/mansionInsert"; // エラーがある場合はフォーム画面に戻る
    }

    // 都道府県名の取り出し
    String selectedPrefecture =
        prefectureList.stream().filter(p -> p.getId().equals(mansionInsertForm.getPrefectureId()))
            .map(EntityPrefecture::getPrefecture) // getPrefectureの値を取得
            .findFirst().orElse(null);
    mansionInsertForm.setPrefecture(selectedPrefecture);


    // ファイルがアップされなかった場合は、noImageを登録する
    if (mansionInsertForm.getImageFile() == null
        || mansionInsertForm.getImageFile().getOriginalFilename().isEmpty()) {
      mansionInsertForm.setImagePath(ImageEnum.IMAGE.getName() + ImageEnum.NO_IMAGE.getName());
    } else {
      // ファイルのアップロード
      try {
        fileHelper.multipartToFile(mansionInsertForm.getImageFile());
      } catch (Exception e) {
        e.printStackTrace();
        return entry(model);
      }
      mansionInsertForm.setImagePath(
          ImageEnum.IMAGE.getName() + mansionInsertForm.getImageFile().getOriginalFilename());
    }

    return "mansion/mansionConfirm";
  }

  @PostMapping("complete")
  public String complete(MansionInsertForm mansionRegistForm, SessionStatus sessionStatus,
      Model model, RedirectAttributes redirectAttributes) {
    EntityMansion mansion = new EntityMansion();
    mansion.setName(mansionRegistForm.getName());
    mansion.setAddress(mansionRegistForm.getAddress());
    mansion.setPrefectureId(mansionRegistForm.getPrefectureId());
    mansion.setMonthlyPrice(mansionRegistForm.getMonthlyPrice());
    mansion.setBuildingDate(mansionRegistForm.getBuildingDate());
    mansion.setImagePath(mansionRegistForm.getImagePath());
    mansionService.insert(mansion);

    redirectAttributes.addAttribute("name", mansionRegistForm.getName());
    sessionStatus.setComplete();
    return "redirect:/mansionComplete";
  }

  @GetMapping("mansionComplete")
  public String mansionComplete(@RequestParam("name") String name, Model model) {
    model.addAttribute("name", name);
    return "mansion/mansionComplete";
  }

  @PostMapping(value = "complete", params = "entryBack")
  public String entryBack(MansionInsertForm mansionRegistForm) {
    // noImage以外のファイルの場合削除
    if (!(mansionRegistForm.getImagePath().endsWith(ImageEnum.NO_IMAGE.getName()))) {
      // アップロードされたファイルを削除
      fileHelper.fileDelete(mansionRegistForm.getImageFile());
    }
    return "redirect:/mansionInsert";
  }

  @InitBinder("mansionInsertForm")
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(monthlyPriceValidator);
  }
}
