package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RoleRequestDto(

        @NotBlank(message = "O nome da role é obrigatório!")
        @Size(min = 3, max = 50, message = "O nome da role deve ter entre 3 e 50 caracteres")
        @Pattern(regexp = "ROLE_[A-Z_]+", message = "O nome da role deve começar com 'ROLE_' seguido de letras maiúsculas")
        String nmRole

) {}