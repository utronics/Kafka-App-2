package com.updateservice.service;

import com.updateservice.model.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class EmpKafkaConsumerService {

    @Autowired
    private EmpKafkaProducerService empKafkaProducerService;
    @Autowired
    private ReactiveKafkaConsumerTemplate<String, EmployeeRequest> reactiveKafkaConsumerTemplate;

    @Value(value = "${EMP_UPDATES_TOPIC}")
    private String empTopic;
    public Flux<EmployeeRequest> consumeAppUpdates() {
        log.info("In consumeAppUpdates()");
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(employeeRequest -> {
                    log.info("successfully consumed {}={}", EmployeeRequest.class.getSimpleName(), employeeRequest);
                    empKafkaProducerService.sendMessages(employeeRequest,empTopic);
                })
                .doOnError(throwable -> log.error("something went wrong while consuming : {}", throwable.getMessage()));
    }

    @PostConstruct
    public void init() {
        log.info("In init()");
        this.consumeAppUpdates().subscribe();
    }

}
