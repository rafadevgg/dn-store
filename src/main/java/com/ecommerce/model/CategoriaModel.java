package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_CATEGORIA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CategoriaModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDCATEGORIA")
    private Long cdCategoria;

    @Column(name = "NMCATEGORIA")
    private String nmCategoria;

    @Column(name = "DSCATEGORIA")
    private String dsCategoria;

    @Column(name = "DSSLUG")
    private String dsSlug;

}
