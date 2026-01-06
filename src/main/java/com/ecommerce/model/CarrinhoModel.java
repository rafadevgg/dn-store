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
@Table(name = "TB_CARRINHO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CarrinhoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDCARRINHO")
    private Long cdCarrinho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", referencedColumnName = "CDUSUARIO")
    private UsuarioModel cdUsuario;

    @CreationTimestamp
    @Column(name = "DTCRIACAO")
    private LocalDateTime dtCriacao;

    @LastModifiedDate
    @Column(name = "DTATUALIZACAO")
    private LocalDateTime dtAtualizacao;

}
