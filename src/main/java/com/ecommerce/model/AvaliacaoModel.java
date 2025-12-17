package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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



}
