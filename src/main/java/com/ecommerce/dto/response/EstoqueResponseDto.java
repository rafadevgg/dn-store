package com.ecommerce.dto.response;

import java.time.LocalDateTime;

public record EstoqueResponseDto(

        Long cdEstoque,
        ProdutoSimplesDto produto,
        Integer qtEstoque,
        Integer qtReservado,
        LocalDateTime dtAtualizacao

) {

    public record ProdutoSimplesDto(

            Long cdProduto,
            String nmProduto

    ) {}

}
