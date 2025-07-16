package testApp.serviceTest;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import myApp.App;
import myApp.service.NotificationServiceImpl;
import myApp.userMessageKafka.UserMessageKafka;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(GreenMailExtension.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NotificationServiceTest {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    private GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @Test
    void sendValidNotificationTest() {
        UserMessageKafka userMessageKafka = new UserMessageKafka("user@example.com", "Create");
        notificationServiceImpl.sendNotification(userMessageKafka);
    }

    @Test
    void sendInvalidNotificationTest() {
        UserMessageKafka userMessageKafka = new UserMessageKafka(null, "Create");
        Assertions.assertThrows(NullPointerException.class, () -> notificationServiceImpl.sendNotification(userMessageKafka));
    }
}
