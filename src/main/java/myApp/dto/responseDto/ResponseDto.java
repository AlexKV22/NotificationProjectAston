package myApp.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO для ответа с информацией об отправке")
public record ResponseDto(
        @NotNull(message = "Email не может быть null")
        @NotBlank(message = "Email не может быть пустым")
        @Schema(description = "Email в ответе")
        String email,
        @NotNull(message = "CreateOrDelete не может быть null")
        @NotBlank(message = "CreateOrDelete не может быть пустым")
        @Schema(description = "Информация об статусе отправки")
        String createOrDelete){}
