package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;

public record ProdutoRequestDto(

        @NotNull(message = "O código da categoria é obrigatório!")
        Long cdCategoria,

        @NotBlank(message = "O nome do produto é obrigatório!")
        @Size(min = 3, max = 200, message = "O nome deve ter entre 3 e 200 caracteres")
        String nmProduto,

        @Size(max = 1000, message = "A descrição não pode ter mais de 1000 caracteres")
        String dsProduto,

        @NotBlank(message = "O slug é obrigatório!")
        @Size(min = 3, max = 200, message = "O slug deve ter entre 3 e 200 caracteres")
        String dsSlug,

        @NotNull(message = "O valor do produto é obrigatório!")
        @Min(value = 0, message = "O valor do produto não pode ser negativo")
        Double vlProduto,

        @Size(max = 500, message = "A URL da imagem não pode ter mais de 500 caracteres")
        String dsImagemUrl,

        Boolean stAtivo

) {}