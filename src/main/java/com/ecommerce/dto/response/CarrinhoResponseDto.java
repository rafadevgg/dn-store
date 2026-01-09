package com.ecommerce.dto.response;

import java.time.LocalDateTime;

public record CarrinhoResponseDto(

        Long cdCarrinho,
        UsuarioSimplesDto usuario,
        LocalDateTime dtCriacao,
        LocalDateTime dtAtualizacao

) {

    public record UsuarioSimplesDto(

            Long cdUsuario,
            String nmUsuario

    ) {}

}
