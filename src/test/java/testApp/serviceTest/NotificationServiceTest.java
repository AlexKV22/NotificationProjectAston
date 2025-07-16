package testApp.serviceTest;

import myApp.App;
import myApp.service.NotificationServiceImpl;
import myApp.userMessageKafka.UserMessageKafka;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;
import java.io.IOException;


@SpringBootTest(classes = App.class)
class NotificationServiceTest {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    private static Wiser wiser;
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @BeforeAll
    static void setUp() {
        System.setProperty("jakarta.activation.MailcapCommandMap", "org.eclipse.angus.activation.MailcapCommandMap");
        wiser = new Wiser();
        wiser.setPort(2525);
        wiser.setHostname("localhost");
        wiser.start();
    }

    @AfterAll
    static void tearDown() {
        wiser.stop();
    }

    @Test
    void sendValidNotificationTest() throws MessagingException, IOException {
        UserMessageKafka userMessageKafka = new UserMessageKafka("EMAIL", "Create");
        UserMessageKafka userMessageKafkaResponse = notificationServiceImpl.sendNotification(userMessageKafka);
        WiserMessage wiserMessage = wiser.getMessages().stream().findFirst().get();
        String content = (String)wiserMessage.getMimeMessage().getContent();
        Assertions.assertNotNull(wiserMessage);
        Assertions.assertEquals(wiserMessage.getEnvelopeReceiver(), userMessageKafkaResponse.getEmail());
        Assertions.assertEquals("Здравствуйте! Ваш аккаунт на сайте был успешно создан.", content);

    }

    @Test
    void sendInvalidNotificationTest() {
        UserMessageKafka userMessageKafka = new UserMessageKafka(null, "Create");
        Assertions.assertThrows(NullPointerException.class, () -> notificationServiceImpl.sendNotification(userMessageKafka));
    }
}
