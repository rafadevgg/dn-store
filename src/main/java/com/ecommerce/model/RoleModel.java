package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_ROLE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class RoleModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDROLE")
    private Long cdRole;

    @Column(name = "NMROLE")
    private String nmRole;

}
