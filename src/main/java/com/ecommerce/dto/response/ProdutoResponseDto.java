package com.ecommerce.dto.response;

import java.time.LocalDateTime;

public record ProdutoResponseDto(

        Long cdProduto,
        CategoriaSimplesDto categoria,
        String nmProduto,
        String dsProduto,
        String dsSlug,
        Double vlProduto,
        String dsImagemUrl,
        Boolean stAtivo,
        LocalDateTime dtCriacao,
        LocalDateTime dtAtualizacao

) {

    public record CategoriaSimplesDto(

            Long cdCategoria,
            String nmCategoria

    ) {}

}
