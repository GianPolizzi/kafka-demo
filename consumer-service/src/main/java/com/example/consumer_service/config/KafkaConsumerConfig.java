package com.example.consumer_service.config;

import com.example.shared_module.Constants;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final ConsumerFactory<String, Object> consumerFactory;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaConsumerConfig(ConsumerFactory<String, Object> consumerFactory,
                               KafkaTemplate<String, Object> kafkaTemplate) {
        this.consumerFactory = consumerFactory;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // 1. Definiamo la strategia di recupero DLT
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                this.kafkaTemplate,
                (record, exception) -> {
                    //String originalTopic = ((ConsumerRecord<?,?>)record).topic();
                    return new TopicPartition(Constants.TOPIC_DLT, -1); // -1 per partizione automatica
                }
        );

        // 2. Definiamo la strategia di BackOff (tentativi)
        // Tentiamo 3 volte con un intervallo di 1 secondo (1000ms) tra i tentativi
        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 3L);

        // 3. Applichiamo la strategia DLT all'errore handler
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, fixedBackOff);

        factory.setConcurrency(1); // Mantiene 1 thread per il consumer
        factory.setCommonErrorHandler(errorHandler); // Assegna l'handler DLT

        return factory;
    }
}
