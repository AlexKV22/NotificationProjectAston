package myApp.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import myApp.converter.NotificationMapper;
import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.service.NotificationService;
import myApp.userMessageKafka.UserMessageKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title = "API отправки письма на почту", description = "API отвечает за отправку сообщения на почту", version = "1.0"),
                    servers = @Server(url = "http://localhost:8081"))
@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationController(NotificationService notificationService, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }




    @Operation(summary = "Отправка сообщения",
            description = "Отправляет сообщение, возвращает ResponseDto ответа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Письмо успешно отправлено", content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Не отправить сообщение, ошибка на сервере"),
            }
    )
    @PostMapping
    public ResponseEntity<ResponseDto> sendNotification(
                @Valid
                @io.swagger.v3.oas.annotations.parameters.RequestBody (
                        description = "Структура запроса отправки письма",
                        required = true,
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = RequestDto.class),
                                examples = @ExampleObject(value = "{ \"email\": \"45sdsd@mail.ru\", \"createordelete\": \"Create\"}"))
                )
                @RequestBody RequestDto requestDto
    ) {
        UserMessageKafka userMessageKafka = notificationMapper.dtoToEntity(requestDto);
        UserMessageKafka userMessageKafkaReady = notificationService.sendNotification(userMessageKafka);
        ResponseDto responseDto = notificationMapper.entityToDto(userMessageKafkaReady);
        return ResponseEntity.ok(responseDto);
    }
}
