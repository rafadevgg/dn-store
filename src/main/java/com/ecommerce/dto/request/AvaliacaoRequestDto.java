package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;

public record AvaliacaoRequestDto(

        @NotNull(message = "O código do produto é obrigatório!")
        Long cdProduto,

        @NotNull(message = "O código do usuário é obrigatório!")
        Long cdUsuario,

        @NotNull(message = "A nota é obrigatória!")
        @Min(value = 1, message = "A nota mínima é 1")
        @Max(value = 5, message = "A nota máxima é 5")
        Integer nrNota,

        @Size(max = 500, message = "O comentário não pode ter mais de 500 caracteres!")
        String dsComentario

) {}