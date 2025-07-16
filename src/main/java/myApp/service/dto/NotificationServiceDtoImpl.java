package myApp.service.dto;

import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.service.NotificationService;
import myApp.userMessageKafka.UserMessageKafka;
import myApp.converter.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceDtoImpl implements NotificationServiceDto {
    private final NotificationMapper notificationMapper;
    private final NotificationService notificationService;

    @Autowired
    public NotificationServiceDtoImpl(NotificationMapper notificationMapper, NotificationService notificationService) {
        this.notificationMapper = notificationMapper;
        this.notificationService = notificationService;
    }

    @Override
    public ResponseDto sendNotification(RequestDto requestDto) {
        UserMessageKafka userMessageKafka = notificationMapper.dtoToEntity(requestDto);
        UserMessageKafka response = notificationService.sendNotification(userMessageKafka);
        return notificationMapper.entityToDto(response);
    }
}
