package myApp.service;

import myApp.userMessageKafka.UserMessageKafka;

public interface NotificationService {
    UserMessageKafka sendNotification(UserMessageKafka userMessageKafka);
}
