package myApp.kafkaConsumer;

import myApp.service.NotificationService;
import myApp.userMessageKafka.UserMessageKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final NotificationService notificationService;

    @Autowired
    public KafkaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "${app.kafka.topic}", groupId = "${app.kafka.groupId}")
    public void listen(UserMessageKafka userMessageKafka) {
        logger.info("Получено raw сообщение: '{}'", userMessageKafka);
        notificationService.sendNotification(userMessageKafka);
        logger.debug("Kafka Consumer успешно принял сообщение и передал на отправку в сервис");
    }
}
