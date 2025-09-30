package com.example.consumer_service.config;

import com.example.shared_module.MessageData;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
public class KafkaStreamProcessor {

    @Value("${input.topic}")
    private String inputTopic;

    @Value("${output.topic}")
    private String outputTopic;

    @Bean
    public KStream<String, MessageData> processStream(StreamsBuilder builder) {

        System.out.println("--- KAFKA STREAMS TOPOLOGY IS BEING BUILT ---");

        // 1. Definiamo i SerDes per il valore (MessageData)
        JsonSerde<MessageData> messageSerde = new JsonSerde<>(MessageData.class);

        // 2. Creiamo il KStream a partire dal topic di input
        KStream<String, MessageData> stream = builder.stream(inputTopic, Consumed.with(Serdes.String(), messageSerde));

        // 3. Elaborazione del Flusso: Raggruppamento e Conteggio
        stream
                // 3.1. Riassegniamo la chiave (usiamo il sender come chiave per il conteggio)
                .selectKey((key, value) -> value.getSender())

                // 3.2. Raggruppiamo per la nuova chiave (sender)
                .groupByKey(org.apache.kafka.streams.kstream.Grouped.with(
                                Serdes.String(),
                                messageSerde)
                )

                // 3.3. Contiamo gli elementi per ciascun sender e li memorizziamo
                .count(Materialized.as("count-by-sender"))

                // 3.4. Convertiamo il KTable in un KStream per l'output
                .toStream()

                // 3.5. Scriviamo il risultato nel topic di output (Chiave: String, Valore: Long)
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));

        return stream;
    }
}
