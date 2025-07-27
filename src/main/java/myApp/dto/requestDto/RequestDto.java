package myApp.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO для отправки сообщения")
public record RequestDto(
        @NotNull(message = "Email не может быть null")
        @NotBlank(message = "Email не может быть пустым")
        @Schema(description = "Email в запросе")
        String email,
        @NotNull(message = "CreateOrDelete не может быть null")
        @NotBlank(message = "CreateOrDelete не может быть пустым")
        @Schema(description = "Статус создания или удаления")
        String createOrDelete){}
