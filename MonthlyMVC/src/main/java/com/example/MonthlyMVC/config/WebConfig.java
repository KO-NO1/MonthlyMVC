package com.example.MonthlyMVC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.MonthlyMVC.util.ImageEnum;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(ImageEnum.IMAGE.getName()+"**").addResourceLocations("file:///"+ImageEnum.IMAGE_DIR.getName());
	}
}