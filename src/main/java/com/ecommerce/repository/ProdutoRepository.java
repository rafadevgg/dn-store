package com.ecommerce.repository;

import com.ecommerce.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

    List<ProdutoModel> findByStAtivoTrue();
    List<ProdutoModel> findByCategoria_CdCategoria(Long cdCategoria);
    List<ProdutoModel> findByNmProdutoContainingIgnoreCase(String nome);

}
