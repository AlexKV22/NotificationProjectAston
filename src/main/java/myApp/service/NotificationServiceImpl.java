package myApp.service;

import myApp.emailSender.EmailSender;
import myApp.exceptions.InvalidArgumentException;
import myApp.exceptions.MailSendException;
import myApp.userMessageKafka.UserMessageKafka;
import myApp.util.SendPhrases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Value("${app.mail.domen}")
    private String domenName;
    private final EmailSender emailSender;

    @Autowired
    public NotificationServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    @Override
    public UserMessageKafka sendNotification(UserMessageKafka userMessageKafka) {
        if (userMessageKafka == null || userMessageKafka.getEmail() == null || userMessageKafka.getCreateOrDelete() == null) {
            logger.warn("Email null или userMessageKafka null или CreateOrDelete null");
            throw new InvalidArgumentException("Email null или userMessageKafka null или CreateOrDelete null");
        } else if (userMessageKafka.getCreateOrDelete().equals("Delete")) {
            emailSender.sendEmailDelete(userMessageKafka.getEmail(), domenName);
            userMessageKafka.setCreateOrDelete(String.format("Произошло удаление аккаунта и уведомление на почту: %s", userMessageKafka.getEmail()));
        } else if (userMessageKafka.getCreateOrDelete().equals("Create")) {
            emailSender.sendEmailCreate(userMessageKafka.getEmail(), domenName);
            userMessageKafka.setCreateOrDelete(String.format("Произошло добавление нового юзера и уведомление на почту: %s", userMessageKafka.getEmail()));
        }
        logger.debug("NotificationServiceImpl успешно завершил работу");
        return userMessageKafka;
    }
}
