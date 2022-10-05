package com.kkamjidot.api.mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄링을 위해 추가
@SpringBootApplication
public class MonoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonoApplication.class, args);
	}

}
