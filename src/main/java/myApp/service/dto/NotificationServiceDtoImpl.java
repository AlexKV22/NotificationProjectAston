package myApp.service.dto;

import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.service.NotificationService;
import myApp.userTempKafka.UserTempKafka;
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
        UserTempKafka userTempKafka = notificationMapper.dtoToEntity(requestDto);
        UserTempKafka response = notificationService.sendNotification(userTempKafka);
        return notificationMapper.entityToDto(response);
    }
}
