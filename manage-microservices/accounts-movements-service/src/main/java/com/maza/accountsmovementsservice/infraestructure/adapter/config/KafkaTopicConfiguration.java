package com.maza.accountsmovementsservice.infraestructure.adapter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${kafka.replics}")
    int replics;
    @Value("${kafka.partitions}")
    int partitions;

    @Bean
    public NewTopic generateTopic(){
        Map<String, String> config = new HashMap<>();
        config.put(TopicConfig.CLEANUP_POLICY_CONFIG,TopicConfig.CLEANUP_POLICY_DELETE);//delete(borra mensaje, compact(retiene el mas actual))
        config.put(TopicConfig.RETENTION_MS_CONFIG,"86400000");//tiempo que guarde los mensajes luego de eesto los elimina
        config.put(TopicConfig.SEGMENT_BYTES_CONFIG,"1073741824");//TAMAÑO MAXIMO del segmemtp en bytes 1 tera por defecto
        config.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG,"1000012");//tamanio maximo de cada mensaje por defecto 1mb
        return TopicBuilder.name("transferTopic")
                .replicas(replics)
                .partitions(partitions)
                .configs(config)
                .build();
    }
}
