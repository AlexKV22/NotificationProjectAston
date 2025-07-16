package testApp.restTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import myApp.App;
import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.rest.NotificationController;
import myApp.service.dto.NotificationServiceDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = App.class)
class RestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationServiceDto notificationServiceDto;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void sendNotificationTest() throws Exception {
        RequestDto requestDto = new RequestDto("erttt", "Create");
        ResponseDto responseDto = new ResponseDto("erttt", "Произошло добавление нового юзера и уведомление на почту: erttt");
        Mockito.when(notificationServiceDto.sendNotification(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api").content(objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.email").value("erttt")).andExpect(jsonPath("$.createOrDelete").value("Произошло добавление нового юзера и уведомление на почту: erttt"));
        Mockito.verify(notificationServiceDto, Mockito.times(1)).sendNotification(requestDto);
    }

    @Test
    void sendNotificationExceptionTest() throws Exception {
        RequestDto requestDto = new RequestDto(null, "Create");
        mockMvc.perform(post("/api").content(objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
