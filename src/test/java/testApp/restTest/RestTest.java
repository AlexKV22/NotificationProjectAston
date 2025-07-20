package testApp.restTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import myApp.App;
import myApp.assembler.NotificationModelAssembler;
import myApp.converter.NotificationMapper;
import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.rest.NotificationController;
import myApp.service.NotificationService;
import myApp.userMessageKafka.UserMessageKafka;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = App.class)
class RestTest {

    @MockBean
    private NotificationMapper notificationMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @MockBean
    private NotificationModelAssembler notificationModelAssembler;


    @Test
    void sendNotificationTest() throws Exception {
        RequestDto requestDto = new RequestDto("erttt", "Create");
        UserMessageKafka userMessageKafka = new UserMessageKafka("erttt", "Create");
        UserMessageKafka userMessageKafkaFromService = new UserMessageKafka("erttt", "Произошло добавление нового юзера и уведомление на почту: erttt");
        ResponseDto responseDto = new ResponseDto("erttt", "Произошло добавление нового юзера и уведомление на почту: erttt");
        Mockito.when(notificationMapper.dtoToEntity(requestDto)).thenReturn(userMessageKafka);
        Mockito.when(notificationService.sendNotification(userMessageKafka)).thenReturn(userMessageKafkaFromService);
        EntityModel<ResponseDto> responseDtoEntityModel = EntityModel.of(responseDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificationController.class).sendNotification(null)).withSelfRel()
        );
        Mockito.when(notificationModelAssembler.toModel(userMessageKafkaFromService)).thenReturn(responseDtoEntityModel);

        mockMvc.perform(post("/api").content(objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.email").value("erttt")).andExpect(jsonPath("$.createOrDelete").value("Произошло добавление нового юзера и уведомление на почту: erttt"));
        Mockito.verify(notificationService, Mockito.times(1)).sendNotification(userMessageKafka);
    }

    @Test
    void sendNotificationExceptionTest() throws Exception {
        RequestDto requestDto = new RequestDto(null, "Create");
        mockMvc.perform(post("/api").content(objectMapper.writeValueAsString(requestDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
