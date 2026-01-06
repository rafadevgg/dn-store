package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_AVALIACAO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AvaliacaoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDAVALIACAO")
    private Long cdAvaliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDPRODUTO", referencedColumnName = "CDPRODUTO")
    private ProdutoModel cdProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", referencedColumnName = "CDUSUARIO")
    private UsuarioModel cdUsuario;

    @Column(name = "NRNOTA")
    private Integer nrNota;

    @Column(name = "DSCOMENTARIO")
    private String dsComentario;

    @CreationTimestamp
    @Column(name = "DTAVALIACAO")
    private LocalDateTime dtAvaliacao;

}
