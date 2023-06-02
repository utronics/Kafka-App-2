package com.updateservice;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class UpdateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpdateServiceApplication.class, args);
	}

//	@Bean
//	public NewTopic empUpdates() {
//		return TopicBuilder.name("emp_updates")
//				.partitions(3)
//				.replicas(1)
//				.compact()
//				.build();
//	}


}
