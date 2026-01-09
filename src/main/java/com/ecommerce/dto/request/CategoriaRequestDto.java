package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDto(

        @NotBlank(message = "O nome da categoria é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nmCategoria,

        @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres")
        String dsCategoria,

        @NotBlank(message = "O slug é obrigatório!")
        @Size(min = 3, max = 100, message = "O slug deve ter entre 3 e 100 caracteres")
        String dsSlug

) {}