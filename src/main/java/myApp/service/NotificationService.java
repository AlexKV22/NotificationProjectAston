package myApp.service;

import myApp.userTempKafka.UserTempKafka;

public interface NotificationService {
    UserTempKafka sendNotification(UserTempKafka userTempKafka);
}
