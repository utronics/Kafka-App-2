package com.updateservice.service;

import com.updateservice.model.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmpKafkaProducerService {

    @Autowired
    private ReactiveKafkaProducerTemplate<String, EmployeeRequest> reactiveKafkaProducerTemplate;

    public void sendMessages(EmployeeRequest employeeRequest, String topic) {
        log.info("send to topic={}, {}={},", topic, EmployeeRequest.class.getSimpleName(), employeeRequest);
        reactiveKafkaProducerTemplate.send(topic, String.valueOf(employeeRequest.getId()), employeeRequest)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}", employeeRequest,
                        senderResult.recordMetadata().offset()))
                .subscribe();
    }

}
