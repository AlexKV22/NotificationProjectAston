package myApp.converter;

import myApp.dto.requestDto.RequestDto;
import myApp.dto.responseDto.ResponseDto;
import myApp.userTempKafka.UserTempKafka;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createOrDelete", source = "createOrDelete")
    ResponseDto entityToDto(UserTempKafka userTempKafka);


    @Mapping(target = "email", source = "email")
    @Mapping(target = "createOrDelete", source = "createOrDelete")
    UserTempKafka dtoToEntity(RequestDto requestDto);
}
