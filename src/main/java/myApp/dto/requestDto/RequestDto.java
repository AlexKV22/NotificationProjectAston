package myApp.dto.requestDto;

import jakarta.validation.constraints.NotNull;

public record RequestDto(
        @NotNull(message = "Email is cannot be null")
        String email,
        @NotNull(message = "CreateOrDelete is cannot be null")
        String createOrDelete){}
