package com.example.MonthlyMVC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.MonthlyMVC.util.ImageEnum;

/**
 * WebMvcConfigurerを実装したクラス 
 * 画像のURLを指定するためのクラス
 */
@Configuration //Spring Boot アプリケーションの起動時に自動的に検出
public class WebConfig implements WebMvcConfigurer {
	/**
	 *  image で始まるリクエストをローカルディレクトリ image にマッピング
	 * @param registry ResourceHandlerRegistry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(ImageEnum.IMAGE.getName()+"**").addResourceLocations("file:///"+ImageEnum.IMAGE_DIR.getName());
		registry.addResourceHandler("/favicon.ico")
        .addResourceLocations("classpath:/static/");
	}
}