package com.chenjin.springbootshiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages = {"com.chenjin.springbootshiro.mapper"}) //告诉容器扫描那个包下的Mapper
public class DemoshiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoshiroApplication.class, args);
	}
}
