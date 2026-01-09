package com.ecommerce.dto.response;

public record ItemPedidoResponseDto(

        Long cdItemPedido,
        PedidoSimplesDto pedido,
        ProdutoSimplesDto produto,
        Integer qtItem,
        Double vlUnitario,
        Double vlSubTotal

) {

    public record PedidoSimplesDto(

            Long cdPedido

    ) {}

    public record ProdutoSimplesDto(

            Long cdProduto,
            String nmProduto

    ) {}

}
