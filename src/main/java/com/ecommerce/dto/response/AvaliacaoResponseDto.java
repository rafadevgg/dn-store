package com.ecommerce.dto.response;

import java.time.LocalDateTime;

public record AvaliacaoResponseDto(

        Long cdAvaliacao,
        ProdutoSimplesDto produto,
        UsuarioSimplesDto usuario,
        Integer nrNota,
        String dsComentario,
        LocalDateTime dtAvaliacao

) {

    public record ProdutoSimplesDto(

            Long cdProduto,
            String nmProduto

    ) {}

    public record UsuarioSimplesDto(

            Long cdUsuario,
            String nmUsuario

    ) {}

}
