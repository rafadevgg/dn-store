package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRODUTO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class ProdutoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDPRODUTO")
    private Long cdProduto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDCATEGORIA", referencedColumnName = "CDCATEGORIA")
    private CategoriaModel categoria;

    @Column(name = "NMPRODUTO")
    private String nmProduto;

    @Column(name = "DSPRODUTO")
    private String dsProduto;

    @Column(name = "DSSLUG")
    private String dsSlug;

    @Column(name = "VLPRODUTO")
    private Double vlProduto;

    @Column(name = "DSIMAGEMURL")
    private String dsImagemUrl;

    @Column(name = "STATIVO")
    private Boolean stAtivo;

    @CreationTimestamp
    @Column(name = "DTCRIACAO")
    private LocalDateTime dtCriacao;

    @LastModifiedDate
    @Column(name = "DTATUALIZACAO")
    private LocalDateTime dtAtualizacao;

}
