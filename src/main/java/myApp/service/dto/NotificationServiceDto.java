package myApp.service.dto;

import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;

public interface NotificationServiceDto {
    ResponseDto sendNotification(RequestDto requestDto);
}
