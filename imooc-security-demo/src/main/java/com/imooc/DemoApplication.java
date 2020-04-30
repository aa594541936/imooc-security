package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
---
新增知识点：
1.@RequestBody 映射请求体到java方法的参数

2.日期类型参数的处理

3.@Valid注解和 BindingResult验证请求参数的合法性并处理校验结果
---
 */
@SpringBootApplication
@RestController
public class DemoApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "hello spring security";
	}

}
