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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_USUARIO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class UsuarioModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDUSUARIO")
    private Long cdUsuario;

    @Column(name = "NMUSUARIO")
    private String nmUsuario;

    @Column(name = "DSEMAIL")
    private String dsEmail;

    @Column(name = "DSSENHA")
    private String dsSenha;

    @Column(name = "DSCPF")
    private String dsCpf;

    @Column(name = "DSTELEFONE")
    private String dsTelefone;

    @CreationTimestamp
    @Column(name = "DTCRIACAO")
    private LocalDateTime dtCriacao;

    @LastModifiedDate
    @Column(name = "DTATUALIZACAO")
    private LocalDateTime dtAtualizacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_USUARIO_ROLE",
            joinColumns = @JoinColumn(name = "CDUSUARIO"),
            inverseJoinColumns = @JoinColumn(name = "CDROLE")
    )
    private Set<RoleModel> roles = new HashSet<>();

}
