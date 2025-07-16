package myApp.service;

import myApp.exceptions.MailSendException;
import myApp.userMessageKafka.UserMessageKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public UserMessageKafka sendNotification(UserMessageKafka userMessageKafka) {
        SimpleMailMessage message = new SimpleMailMessage();
        if (userMessageKafka.getCreateOrDelete().equals("Delete")) {
            message.setTo(userMessageKafka.getEmail());
            message.setSubject("Удаление аккаунта");
            message.setFrom("OUR_DOMEN");
            message.setText("Здравствуйте! Ваш аккаунт был удалён.");
            try {
                javaMailSender.send(message);
                logger.debug("Успешно было отправлено уведомление о удалении аккаунта на почту: {}", userMessageKafka.getEmail());
                userMessageKafka.setCreateOrDelete(String.format("Произошло удаление аккаунта и уведомление на почту: %s", userMessageKafka.getEmail()));
            } catch (MailException e) {
                logger.error("Не удалось отправить сообщение на электронную почту : {}", e.getMessage());
                throw new MailSendException("Не удалось отправить сообщение на электронную почту", e.getCause());
            }
        } else {
            message.setTo(userMessageKafka.getEmail());
            message.setSubject("Создание аккаунта");
            message.setFrom("OUR_DOMEN");
            message.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
            try {
                javaMailSender.send(message);
                logger.debug("Успешно было отправлено уведомление о создании аккаунта на почту: {}", userMessageKafka.getEmail());
                userMessageKafka.setCreateOrDelete(String.format("Произошло добавление нового юзера и уведомление на почту: %s", userMessageKafka.getEmail()));
            } catch (MailException e) {
                logger.error("Не удалось отправить сообщение на электронную почту : {}", e.getMessage());
                throw new MailSendException("Не удалось отправить сообщение на электронную почту", e.getCause());
            }
        }
        return userMessageKafka;
    }
}
