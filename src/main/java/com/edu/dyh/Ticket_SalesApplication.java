package com.edu.dyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.session.data.redis.Configration.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@MapperScan("com.edu.dyh.Mapper")
public class Ticket_SalesApplication {
	public static void main(String[] args) {
		SpringApplication.run(Ticket_SalesApplication.class, args);
	}
}
