package myApp.rest;

import jakarta.validation.Valid;
import myApp.dto.requestDto.RequestDto;
import myApp.service.dto.NotificationServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationServiceDto notificationServiceDto;

    @Autowired
    public NotificationController(NotificationServiceDto notificationServiceDto) {
        this.notificationServiceDto = notificationServiceDto;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@Valid @RequestBody RequestDto requestDto) {
        notificationServiceDto.sendNotification(requestDto);
        return ResponseEntity.ok().build();
    }
}
