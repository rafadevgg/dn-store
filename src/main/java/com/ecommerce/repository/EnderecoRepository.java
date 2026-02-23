package com.ecommerce.repository;

import com.ecommerce.model.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {

    List<EnderecoModel> findByUsuario_CdUsuario(Long cdUsuario);
    Optional<EnderecoModel> findByUsuario_CdUsuarioAndStPadraoTrue(Long cdUsuario);

}
