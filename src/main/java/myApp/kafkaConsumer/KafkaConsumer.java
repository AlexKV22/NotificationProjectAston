package myApp.kafkaConsumer;

import myApp.service.NotificationService;
import myApp.userTempKafka.UserTempKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public void listen(UserTempKafka userTempKafka) {
        logger.info("Получено raw сообщение: '{}'", userTempKafka);
        notificationService.sendNotification(userTempKafka);
        logger.debug("Kafka Consumer успешно принял сообщение и передал на отправку в сервис");
    }
}
