package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PEDIDO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PedidoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDPEDIDO")
    private Long cdPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", referencedColumnName = "CDUSUARIO")
    private UsuarioModel cdUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDENDERECO", referencedColumnName = "CDENDERECO")
    private EnderecoModel cdEndereco;

    @Column(name = "NRPEDIDO")
    private String nrPedido;

    @Column(name = "VLTOTAL")
    private Double vlTotal;

    @Column(name = "VLFRETE")
    private Double vlFrete;

    @Column(name = "TPPAGAMENTO")
    private String tpPagamento;

    @Column(name = "STPEDIDO")
    private String stPedido;

    @CreationTimestamp
    @Column(name = "DTPEDIDO")
    private LocalDateTime dtPedido;

    @LastModifiedDate
    @Column(name = "DTATUALIZACAO")
    private LocalDateTime dtAtualizacao;

}
