package myApp.dto.responseDto;

import jakarta.validation.constraints.NotNull;

public record ResponseDto(
        @NotNull(message = "Email is cannot be null")
        String email,
        @NotNull(message = "CreateOrDelete is cannot be null")
        String createOrDelete){}
