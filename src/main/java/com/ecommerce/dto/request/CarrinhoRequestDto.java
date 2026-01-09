package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record CarrinhoRequestDto(

        @NotNull(message = "O código do usuário é obrigatório!")
        Long cdUsuario

) {}