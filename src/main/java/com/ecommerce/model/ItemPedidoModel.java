package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_ITEMPEDIDO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ItemPedidoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDITEMPEDIDO")
    private Long cdItemPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDPEDIDO", referencedColumnName = "CDPEDIDO")
    private PedidoModel pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDPRODUTO", referencedColumnName = "CDPRODUTO")
    private ProdutoModel produto;

    @Column(name = "QTITEM")
    private Integer qtItem;

    @Column(name = "VLUNITARIO")
    private Double vlUnitario;

    @Column(name = "VLSUBTOTAL")
    private Double vlSubTotal;

}
