package com.deeper.homework.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiBasePathConfig implements WebMvcConfigurer {

  @Value("${api.base-path}")
  private String basePath;

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix(basePath, c -> c.isAnnotationPresent(RestController.class));
  }

  public String[] getWhiteListUrls(String[] urls) {
    return Arrays.stream(urls)
        .map(url -> basePath + url)
        .toArray(String[]::new);
  }
}