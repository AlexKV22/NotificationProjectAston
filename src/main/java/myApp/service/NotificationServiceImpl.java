package myApp.service;

import myApp.exceptions.MailSendException;
import myApp.userMessageKafka.UserMessageKafka;
import myApp.util.SendPhrases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final JavaMailSender javaMailSender;
    @Value("${app.mail.domen}")
    private String domenName;

    @Autowired
    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public UserMessageKafka sendNotification(UserMessageKafka userMessageKafka) {
        SimpleMailMessage message = new SimpleMailMessage();
        if (userMessageKafka.getCreateOrDelete().equals("Delete")) {
            message.setTo(userMessageKafka.getEmail());
            message.setSubject(SendPhrases.DELETE_PHRASE_SUBJECT.getPhrase());
            message.setFrom(domenName);
            message.setText(SendPhrases.DELETE_PHRASE.getPhrase());
            try {
//                javaMailSender.send(message);
                logger.debug("Успешно было отправлено уведомление о удалении аккаунта на почту: {}", userMessageKafka.getEmail());
                userMessageKafka.setCreateOrDelete(String.format("Произошло удаление аккаунта и уведомление на почту: %s", userMessageKafka.getEmail()));
            } catch (MailException e) {
                logger.error("Не удалось отправить сообщение на электронную почту : {}", e.getMessage());
                throw new MailSendException("Не удалось отправить сообщение на электронную почту", e.getCause());
            }
        } else {
            message.setTo(userMessageKafka.getEmail());
            message.setSubject(SendPhrases.CREATE_PHRASE_SUBJECT.getPhrase());
            message.setFrom(domenName);
            message.setText(SendPhrases.CREATE_PHRASE.getPhrase());
            try {
//                javaMailSender.send(message);
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
