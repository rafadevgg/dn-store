package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_ENDERECO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EnderecoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDENDERECO")
    private Long cdEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", referencedColumnName = "CDUSUARIO")
    private UsuarioModel cdUsuario;

    @Column(name = "DSLOGRADOURO")
    private String dsLogradouro;

    @Column(name = "DSNUMERO")
    private String dsNumero;

    @Column(name = "DSCOMPLEMENTO")
    private String dsComplemento;

    @Column(name = "DSBAIRRO")
    private String dsBairro;

    @Column(name = "DSCIDADE")
    private String dsCidade;

    @Column(name = "DSESTADO")
    private String dsEstado;

    @Column(name = "DSCEP")
    private String dsCep;

    @Column(name = "STPADRAO")
    private Boolean stPadrao;

}
