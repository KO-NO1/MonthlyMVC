package com.example.MonthlyMVC.util;

public enum ImageEnum {
	IMAGE("/image/"), NO_IMAGE("noImage.png"), IMAGE_DIR("C:\\image\\");

	// フィールドの定義
	private String name;

	// コンストラクタの定義
	private ImageEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
