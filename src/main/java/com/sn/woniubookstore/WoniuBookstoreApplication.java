package com.sn.woniubookstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sn.woniubookstore.mapper")
@SpringBootApplication
public class WoniuBookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoniuBookstoreApplication.class, args);
	}

}
