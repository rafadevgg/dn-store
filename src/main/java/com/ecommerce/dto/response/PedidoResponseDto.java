package com.ecommerce.dto.response;

import java.time.LocalDateTime;

public record PedidoResponseDto(

        Long cdPedido,
        UsuarioSimplesDto usuario,
        EnderecoSimplesDto endereco,
        String nrPedido,
        Double vlTotal,
        Double vlFrete,
        String tpPagamento,
        String stPedido,
        LocalDateTime dtPedido,
        LocalDateTime dtAtualizacao

) {

    public record UsuarioSimplesDto(

            Long cdUsuario,
            String nmUsuario

    ) {}

    public record EnderecoSimplesDto(

            Long cdEndereco,
            String dsCep

    ) {}

}
