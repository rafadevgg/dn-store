package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ESTOQUE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EstoqueModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDESTOQUE")
    private Long cdEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDPRODUTO", referencedColumnName = "CDPRODUTO")
    private ProdutoModel cdProduto;

    @Column(name = "QTESTOQUE")
    private Integer qtEstoque;

    @Column(name = "QTRESERVADO")
    private Integer qtReservado;

    @LastModifiedDate
    @Column(name = "DTATUALIZACAO")
    private LocalDateTime dtAtualizacao;

}
