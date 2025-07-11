package myApp.service;

import myApp.userTempKafka.UserTempKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendNotification(UserTempKafka userTempKafka) {
        SimpleMailMessage message = new SimpleMailMessage();
        if (userTempKafka.getCreateOrDelete().equals("Delete")) {
            message.setTo(userTempKafka.getEmail());
            message.setFrom("OUR_DOMEN");
            message.setText("Здравствуйте! Ваш аккаунт был удалён.");
            javaMailSender.send(message);
        } else {
            message.setTo(userTempKafka.getEmail());
            message.setFrom("OUR_DOMEN");
            message.setText("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
            javaMailSender.send(message);
        }
    }
}
