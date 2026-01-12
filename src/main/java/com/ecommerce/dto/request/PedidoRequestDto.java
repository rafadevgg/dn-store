package com.ecommerce.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record PedidoRequestDto(

        @NotNull(message = "O código do usuário é obrigatório!")
        Long cdUsuario,

        @NotNull(message = "O código do endereço é obrigatório!")
        Long cdEndereco,

        @NotNull(message = "O valor do frete é obrigatório!")
        @Min(value = 0, message = "O valor do frete não pode ser negativo")
        Double vlFrete,

        @NotBlank(message = "O tipo de pagamento é obrigatório!")
        @Pattern(regexp = "CREDITO|DEBITO|PIX|BOLETO", message = "Tipo de pagamento inválido. Use: CREDITO, DEBITO, PIX ou BOLETO")
        String tpPagamento,

        @NotNull(message = "A lista de itens é obrigatória!")
        @Size(min = 1, message = "O pedido deve conter pelo menos 1 item")
        @Valid
        List<ItemPedidoRequest> itens

) {

    public record ItemPedidoRequest(

            @NotNull(message = "O código do produto é obrigatório!")
            Long cdProduto,

            @NotNull(message = "A quantidade é obrigatória!")
            @Min(value = 1, message = "A quantidade mínima é 1")
            Integer qtItem

    ) {}

}