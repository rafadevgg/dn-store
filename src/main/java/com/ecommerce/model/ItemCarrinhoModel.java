package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_ITEMCARRINHO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ItemCarrinhoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDITEMCARRINHO")
    private Long cdItemCarrinho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDCARRINHO", referencedColumnName = "CDCARRINHO")
    private CarrinhoModel cdCarrinho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDPRODUTO", referencedColumnName = "CDPRODUTO")
    private ProdutoModel cdProduto;

    @Column(name = "QTITEM")
    private Integer qtItem;

    @Column(name = "VLUNITARIO")
    private Double vlUnitario;

}
