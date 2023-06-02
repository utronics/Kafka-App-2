package com.updateservice.config;

import com.updateservice.model.EmployeeRequest;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;


@Configuration
public class EmpKafkaProducerConfig {

    @Bean
    public SenderOptions<String, EmployeeRequest> producerProps(KafkaProperties kafkaProperties) {
        return SenderOptions.create(kafkaProperties.buildProducerProperties());
    }
    @Bean
    public ReactiveKafkaProducerTemplate<String, EmployeeRequest> reactiveKafkaProducerTemplate(
            SenderOptions<String, EmployeeRequest> producerProps) {
        return new ReactiveKafkaProducerTemplate<>(producerProps);
    }
}
