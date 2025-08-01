package testApp.serviceTest;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import myApp.App;
import myApp.emailSender.EmailSender;
import myApp.exceptions.InvalidArgumentException;
import myApp.exceptions.MailSendException;
import myApp.service.NotificationServiceImpl;
import myApp.userMessageKafka.UserMessageKafka;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.util.Arrays;


@SpringBootTest(classes = App.class)
@Disabled
class NotificationServiceTest {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);


    @Test
    void sendValidNotificationTest() throws MessagingException, IOException {
        UserMessageKafka userMessageKafka = new UserMessageKafka("user@example.com", "Create");
        UserMessageKafka userMessageKafkaResponse = notificationServiceImpl.sendNotification(userMessageKafka);
        MimeMessage mimeMessage = Arrays.stream(greenMail.getReceivedMessages()).findFirst().get();
        String content = (String)mimeMessage.getContent();
        Assertions.assertNotNull(userMessageKafkaResponse);
        Assertions.assertEquals("user@example.com", userMessageKafkaResponse.getEmail());
        Assertions.assertEquals("Здравствуйте! Ваш аккаунт на сайте был успешно создан.", content);
    }

    @Test
    void sendInvalidNotificationTest() {
        UserMessageKafka userMessageKafka = new UserMessageKafka(null, "Create");
        Assertions.assertThrows(InvalidArgumentException.class, () -> notificationServiceImpl.sendNotification(userMessageKafka));
    }
}
