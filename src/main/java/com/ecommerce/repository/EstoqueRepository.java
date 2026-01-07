package com.ecommerce.repository;

import com.ecommerce.model.EstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<EstoqueModel, Long> {}
