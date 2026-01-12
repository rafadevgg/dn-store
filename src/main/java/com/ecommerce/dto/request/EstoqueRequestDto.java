package com.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EstoqueRequestDto(

        @NotNull(message = "O código do produto é obrigatório!")
        Long cdProduto,

        @NotNull(message = "A quantidade em estoque é obrigatória!")
        @Min(value = 0, message = "A quantidade em estoque não pode ser negativa")
        Integer qtEstoque,

        @Min(value = 0, message = "A quantidade reservada não pode ser negativa")
        Integer qtReservado

) {}