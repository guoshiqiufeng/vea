package com.gitee.fubluesky.vea.server.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yanghq
 * @version 1.0
 * @since 2021-11-18 9:08
 */
@MapperScan(basePackages = "com.gitee.fubluesky.vea.**.mapper")
@SpringBootApplication(scanBasePackages = "com.gitee.fubluesky.vea")
public class SystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

}