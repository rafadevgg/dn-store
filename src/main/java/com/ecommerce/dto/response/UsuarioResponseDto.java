package com.ecommerce.dto.response;

import java.time.LocalDateTime;

public record UsuarioResponseDto(

        Long cdUsuario,
        String nmUsuario,
        String dsEmail,
        String dsCpf,
        String dsTelefone,
        LocalDateTime dtCriacao,
        LocalDateTime dtAtualizacao

) {
}
