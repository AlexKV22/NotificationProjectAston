package myApp.rest;

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

    @PostMapping
    public ResponseEntity<ResponseDto> sendNotification(@Valid @RequestBody RequestDto requestDto) {
        UserMessageKafka userMessageKafka = notificationMapper.dtoToEntity(requestDto);
        UserMessageKafka userMessageKafkaReady = notificationService.sendNotification(userMessageKafka);
        System.out.println(userMessageKafkaReady);
        ResponseDto responseDto = notificationMapper.entityToDto(userMessageKafkaReady);
        return ResponseEntity.ok(responseDto);
    }
}
