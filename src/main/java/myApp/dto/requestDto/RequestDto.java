package myApp.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO для отправки сообщения")
public record RequestDto(
        @NotNull(message = "Email is cannot be null")
        @NotBlank
        @Schema(description = "Email в запросе")
        String email,
        @NotNull(message = "CreateOrDelete is cannot be null")
        @NotBlank
        @Schema(description = "Статус создания или удаления")
        String createOrDelete){}
