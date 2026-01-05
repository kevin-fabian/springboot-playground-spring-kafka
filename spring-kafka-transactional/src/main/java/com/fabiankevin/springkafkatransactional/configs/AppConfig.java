package com.fabiankevin.springkafkatransactional.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Autowired
    private KafkaProperties kafkaProperties;

//    @Bean
//    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

//    @Bean
//    public ProducerFactory<String, PlanetEvent> producerFactory() {
//        Map<String, Object> stringObjectMap = kafkaProperties.getProducer().buildProperties(null);
//        stringObjectMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
//        // Add other Kafka producer properties as needed
////        stringObjectMap.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "your-transactional-id");
//
//        return new DefaultKafkaProducerFactory<>(stringObjectMap);
//    }
//
//    @Bean
//    public KafkaTemplate<String, PlanetEvent> kafkaTemplate(ProducerFactory<String, PlanetEvent> planetProducerFactory) {
////        planetProducerFactory
//        KafkaTemplate<String, PlanetEvent> stringPlanetKafkaTemplate = new KafkaTemplate<>(planetProducerFactory);
//        stringPlanetKafkaTemplate.setDefaultTopic("big-planets");
//
////        stringPlanetKafkaTemplate.set
////        stringPlanetKafkaTemplate.tra
//        return stringPlanetKafkaTemplate;
//    }
//
//    @Bean
//    public KafkaTransactionManager<String, PlanetEvent> kafkaTransactionManager(ProducerFactory<String, PlanetEvent> producerFactory) {
//        KafkaTransactionManager<String, PlanetEvent> stringPlanetEventKafkaTransactionManager = new KafkaTransactionManager<>(producerFactory);
////        stringPlanetEventKafkaTransactionManager.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
////        stringPlanetEventKafkaTransactionManager.setTransactionIdPrefix("tx-");
////        stringPlanetEventKafkaTransactionManager.set
//        return stringPlanetEventKafkaTransactionManager;
//    }

//    @Bean
//    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

//    @Bean
//    public ChainedKafkaTransactionManager<Object, Object> chainedKafkaTransactionManager(
//            KafkaTransactionManager kafkaTransactionManager,
//            JpaTransactionManager jpaTransactionManager) {
//        return new ChainedKafkaTransactionManager<>(kafkaTransactionManager, jpaTransactionManager);
//    }
//@Bean
//public JtaTransactionManager jtaTransactionManager() {
//    return new JtaTransactionManagerFactoryBean().getObject();
//}

//    @Bean
//    public ChainedTransactionManager chainedTransactionManager(
//            JpaTransactionManager jpaTransactionManager,
//            KafkaTransactionManager<String, PlanetEvent> kafkaTransactionManager
//    ) {
//        return new ChainedTransactionManager(jpaTransactionManager, kafkaTransactionManager);
//    }
//    @Bean
//    public PlatformTransactionManager transactionManager(@) throws Throwable {
//        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
//    }

//    @Bean
//    public KafkaTransactionManager<String, PlanetEvent> kafkaTransactionManager(ProducerFactory<String, PlanetEvent> producer){
//        return new KafkaTransactionManager<>(producer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
//            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
//            ConsumerFactory consumerFactory,
//            KafkaTransactionManager<String, PlanetEvent> kafkaTransactionManager) {
//        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
//          new ConcurrentKafkaListenerContainerFactory<>();
//        configurer.configure(factory, consumerFactory);
//        factory.getContainerProperties().setTransactionManager(kafkaTransactionManager);
//        return factory;
//    }

}