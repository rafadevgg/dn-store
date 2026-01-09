package com.ecommerce.dto.response;

public record ItemCarrinhoResponseDto(

        Long cdItemCarrinho,
        CarrinhoSimplesDto carrinho,
        ProdutoSimplesDto produto,
        Integer qtItem,
        Double vlUnitario

) {

    public record CarrinhoSimplesDto(

            Long cdCarrinho

    ) {}

    public record ProdutoSimplesDto(

            Long cdProduto,
            String nmProduto

    ) {}

}
