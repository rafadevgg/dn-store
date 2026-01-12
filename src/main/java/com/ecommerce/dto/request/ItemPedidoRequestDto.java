package com.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDto(

        @NotNull(message = "O código do pedido é obrigatório!")
        Long cdPedido,

        @NotNull(message = "O código do produto é obrigatório!")
        Long cdProduto,

        @NotNull(message = "A quantidade é obrigatória!")
        @Min(value = 1, message = "A quantidade mínima é 1")
        Integer qtItem,

        @NotNull(message = "O valor unitário é obrigatório!")
        @Min(value = 0, message = "O valor unitário não pode ser negativo")
        Double vlUnitario

) {}