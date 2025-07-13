package myApp.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestDto(
        @NotNull(message = "Email is cannot be null")
        @NotBlank
        String email,
        @NotNull(message = "CreateOrDelete is cannot be null")
        @NotBlank
        String createOrDelete){}
