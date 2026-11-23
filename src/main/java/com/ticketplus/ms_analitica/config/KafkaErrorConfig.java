package com.ticketplus.ms_analitica.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaErrorConfig {

    @Value("${kafka.topic.pagos.dlt}")
    private String pagosDltTopic;

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
            kafkaTemplate,
            (record, ex) -> {
                log.error("Reintentos agotados. Enviando mensaje a DLT [{}]. Error: {}", pagosDltTopic, ex.getMessage());
                return new TopicPartition(pagosDltTopic, record.partition());
            }
        );
        // 2 reintentos con 2000ms de espera antes de enviar al DLT
        return new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 2L));
    }
}