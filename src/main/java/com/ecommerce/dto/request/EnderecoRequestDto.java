package com.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDto(

        @NotNull(message = "O código do usuário é obrigatório!")
        Long cdUsuario,

        @NotBlank(message = "O logradouro é obrigatório!")
        @Size(max = 200, message = "O logradouro não pode ter mais de 200 caracteres")
        String dsLogradouro,

        @NotBlank(message = "O número é obrigatório!")
        @Size(max = 20, message = "O número não pode ter mais de 20 caracteres")
        String dsNumero,

        @Size(max = 100, message = "O complemento não pode ter mais de 100 caracteres")
        String dsComplemento,

        @NotBlank(message = "O bairro é obrigatório!")
        @Size(max = 100, message = "O bairro não pode ter mais de 100 caracteres")
        String dsBairro,

        @NotBlank(message = "A cidade é obrigatória!")
        @Size(max = 100, message = "A cidade não pode ter mais de 100 caracteres")
        String dsCidade,

        @NotBlank(message = "O estado é obrigatório!")
        @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres (UF)")
        String dsEstado,

        @NotBlank(message = "O CEP é obrigatório!")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido. Use o formato: 12345-678 ou 12345678!")
        String dsCep,

        Boolean stPadrao

) {}