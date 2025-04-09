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
import com.example.MonthlyMVC.form.MansionInsertForm;
import com.example.MonthlyMVC.model.Prefecture;
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
    List<Prefecture> prefectureList = prefectureService.selectAll();
    model.addAttribute("prefectureList", prefectureList);
    return "mansion/mansionInsert";
  }

  @PostMapping("confirm")
  public String confirm(
      @SessionAttribute(value = "prefectureList", required = false) List<Prefecture> prefectureList,
      @Validated MansionInsertForm mansionInsertForm, BindingResult bindingResult, Model model) {
  
      // 都道府県リストが null の場合、再取得
      if (prefectureList == null) {
          prefectureList = prefectureService.selectAll();
          model.addAttribute("prefectureList", prefectureList);
      }
  
      // 都道府県名の取り出し
      Prefecture selectedPrefecture = prefectureList.stream()
          .filter(p -> p.getId().equals(mansionInsertForm.getPrefectureId()))
          .findFirst()
          .orElse(null);
  
      if (selectedPrefecture != null) {
          mansionInsertForm.setPrefecture(selectedPrefecture.getPrefecture());
      } else {
          bindingResult.rejectValue("prefectureId", "prefectureId.invalid", "無効な都道府県IDです。");
          return entry(model);
      }
  
      // バリデーションエラーがある場合
      if (bindingResult.hasErrors()) {
          bindingResult.getAllErrors().forEach(error -> 
              System.out.println("エラー: " + error.getDefaultMessage())
          );
          return entry(model);
      }
  
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
    return "redirect:/mansionInsert/mansionComplete";
  }

  @GetMapping("mansionComplete")
  public String mansionComplete(@RequestParam("name") String name, Model model) {
    model.addAttribute("name", name);
    return "mansion/mansionComplete";
  }

  @PostMapping(value = "complete", params = "entryBack")
  public String entryBack(MansionInsertForm mansionRegistForm) {
    // noImage以外のファイルの場合削除
    if (!(mansionRegistForm.getImagePath().equals(ImageEnum.NO_IMAGE.getName()))) {
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
