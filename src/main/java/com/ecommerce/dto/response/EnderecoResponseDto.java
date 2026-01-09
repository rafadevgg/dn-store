package com.ecommerce.dto.response;

public record EnderecoResponseDto(

        Long cdEndereco,
        UsuarioSimplesDto usuario,
        String dsLogradouro,
        String dsNumero,
        String dsComplemento,
        String dsBairro,
        String dsCidade,
        String dsEstado,
        String dsCep,
        Boolean stPadrao

) {

    public record UsuarioSimplesDto(

            Long cdUsuario,
            String nmUsuario

    ) {}

}
