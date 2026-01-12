package com.ecommerce.dto.request;

import jakarta.validation.constraints.*;

public record UsuarioRequestDto(

        @NotBlank(message = "O nome do usuário é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nmUsuario,

        @NotBlank(message = "O email é obrigatório!")
        @Email(message = "Email inválido")
        @Size(max = 100, message = "O email não pode ter mais de 100 caracteres")
        String dsEmail,

        @NotBlank(message = "A senha é obrigatória!")
        @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
        String dsSenha,

        @NotBlank(message = "O CPF é obrigatório!")
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}", message = "CPF inválido. Use o formato: 123.456.789-00 ou 12345678900")
        String dsCpf,

        @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "Telefone inválido. Use o formato: (11) 98765-4321 ou 11987654321")
        String dsTelefone

) {}