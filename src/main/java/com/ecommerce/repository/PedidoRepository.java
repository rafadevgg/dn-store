package com.ecommerce.repository;

import com.ecommerce.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    List<PedidoModel> findByUsuario_CdUsuario(Long cdUsuario);
    List<PedidoModel> findByStPedido(String status);

}
