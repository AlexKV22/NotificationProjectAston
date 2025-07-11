package myApp.kafkaListener;

import myApp.service.NotificationService;
import myApp.userTempKafka.UserTempKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {
    private final Logger logger = LoggerFactory.getLogger(KafkaListener.class);
    private final NotificationService notificationService;

    @Autowired
    public KafkaListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "test-topic", groupId = "testgroup")
    public void listen(UserTempKafka userTempKafka) {
        logger.info("Получено raw сообщение: '{}'", userTempKafka);
        notificationService.sendNotification(userTempKafka);
        logger.info("Kafka Consumer успешно принял сообщение и передал на отправку в сервис");
    }
}
