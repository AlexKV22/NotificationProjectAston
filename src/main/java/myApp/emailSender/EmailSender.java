package myApp.emailSender;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import myApp.util.SendPhrases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    private final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Retry(name = "emailRetry")
    @CircuitBreaker(name="MyCircuitBreakerDelete", fallbackMethod = "fallBackDelete")
    public void sendEmailDelete(String email, String domenName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(SendPhrases.DELETE_PHRASE_SUBJECT.getPhrase());
        message.setFrom(domenName);
        message.setText(SendPhrases.DELETE_PHRASE.getPhrase());
          javaMailSender.send(message);
          logger.debug("Успешно было отправлено уведомление о удалении аккаунта на почту: {}", email);
    }

    @Retry(name = "emailRetry")
    @CircuitBreaker(name="MyCircuitBreakerCreate", fallbackMethod = "fallBackCreate")
    public void sendEmailCreate(String email, String domenName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(SendPhrases.CREATE_PHRASE_SUBJECT.getPhrase());
        message.setFrom(domenName);
        message.setText(SendPhrases.CREATE_PHRASE.getPhrase());
        javaMailSender.send(message);
        logger.debug("Успешно было отправлено уведомление о создании аккаунта на почту: {}", email);
    }

    public void fallBackCreate(String email, String domenName, Throwable e) {
        logger.warn("Произошла ошибка отправки на почту, операция {}, сообщение ошибки: {}", SendPhrases.CREATE_PHRASE_SUBJECT.getPhrase(), e.getMessage());
    }

    public void fallBackDelete(String email, String domenName, Throwable e) {
        logger.warn("Произошла ошибка отправления на почту, операция {}, сообщение ошибки: {}", SendPhrases.DELETE_PHRASE_SUBJECT.getPhrase(), e.getMessage());
    }
}
