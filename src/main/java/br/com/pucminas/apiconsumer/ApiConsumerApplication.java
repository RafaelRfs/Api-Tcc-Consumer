package br.com.pucminas.apiconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ApiConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConsumerApplication.class);
		log.info("API Consumer Emails Online Versao 1.0");
	}

}
