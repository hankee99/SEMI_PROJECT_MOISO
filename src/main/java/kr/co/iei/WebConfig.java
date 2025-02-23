package kr.co.iei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//스프링부트 설정파일임을 나타내는 어노테이션
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	@Value(value="${file.root}")
	private String root;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/**")
		.addResourceLocations("classpsth:/templates/","classpath:/static/");
		registry
			.addResourceHandler("/moisoPhoto/**")
			.addResourceLocations("file:///"+root+"/moisoPhoto/");
		registry
			.addResourceHandler("/groupThumb/**")
			.addResourceLocations("file:///"+root+"/groupThumb/");
		registry
		.addResourceHandler("/profile/**")
		.addResourceLocations("file:///"+root+"/profile/");
		registry
		.addResourceHandler("/groupEditor/**")
		.addResourceLocations("file:///"+root+"/groupEditor/");
	}
}
