package myApp.converter;

import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.userMessageKafka.UserMessageKafka;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
@Component
@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createOrDelete", source = "createOrDelete")
    ResponseDto entityToDto(UserMessageKafka userMessageKafka);


    @Mapping(target = "email", source = "email")
    @Mapping(target = "createOrDelete", source = "createOrDelete")
    UserMessageKafka dtoToEntity(RequestDto requestDto);
}
