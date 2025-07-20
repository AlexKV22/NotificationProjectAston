package myApp.assembler;

import myApp.converter.NotificationMapper;
import myApp.dto.responseDto.ResponseDto;
import myApp.rest.NotificationController;
import myApp.userMessageKafka.UserMessageKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class NotificationModelAssembler implements RepresentationModelAssembler<UserMessageKafka, EntityModel<ResponseDto>> {

    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationModelAssembler(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Override
    public EntityModel<ResponseDto> toModel(UserMessageKafka entity) {
        ResponseDto responseDto = notificationMapper.entityToDto(entity);
        return EntityModel.of(responseDto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificationController.class).sendNotification(null)).withSelfRel()
        );
    }
}
