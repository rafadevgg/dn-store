package com.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemCarrinhoRequestDto(

        @NotNull(message = "O código do carrinho é obrigatório!")
        Long cdCarrinho,

        @NotNull(message = "O código do produto é obrigatório!")
        Long cdProduto,

        @NotNull(message = "A quantidade é obrigatória!")
        @Min(value = 1, message = "A quantidade mínima é 1")
        Integer qtItem

) {}