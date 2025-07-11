package myApp.service;

import myApp.userTempKafka.UserTempKafka;

public interface NotificationService {
    void sendNotification(UserTempKafka userTempKafka);
}
