package myApp.dto.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO для ответа с информацией об отправке")
public record ResponseDto(
        @NotNull(message = "Email is cannot be null")
        @NotBlank
        @Schema(description = "Email в ответе")
        String email,
        @NotNull(message = "CreateOrDelete is cannot be null")
        @NotBlank
        @Schema(description = "Информация об статусе отправки")
        String createOrDelete){}
