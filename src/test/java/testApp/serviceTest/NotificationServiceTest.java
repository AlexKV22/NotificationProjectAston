package testApp.serviceTest;

import myApp.App;
import myApp.service.NotificationService;
import myApp.userTempKafka.UserTempKafka;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;


@SpringBootTest(classes = App.class)
class NotificationServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    private static Wiser wiser;

    @InjectMocks
    NotificationService notificationService;

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
    void sendValidNotificationTest() {
        UserTempKafka userTempKafka = new UserTempKafka("EMAIL", "Create");
        UserTempKafka userTempKafkaResponse = notificationService.sendNotification(userTempKafka);
        WiserMessage wiserMessage = wiser.getMessages().stream().findFirst().get();
        Assertions.assertNotNull(wiserMessage);
        Assertions.assertEquals(wiserMessage.getEnvelopeReceiver(), userTempKafkaResponse.getEmail());
        Assertions.assertEquals(userTempKafkaResponse.getCreateOrDelete(), "Произошло добавление нового юзера и уведомление на почту: EMAIL");
    }

    @Test
    void sendInvalidNotificationTest() {
        UserTempKafka userTempKafka = new UserTempKafka(null, "Create");
        Assertions.assertThrows(NullPointerException.class, () -> notificationService.sendNotification(userTempKafka));
    }
}
