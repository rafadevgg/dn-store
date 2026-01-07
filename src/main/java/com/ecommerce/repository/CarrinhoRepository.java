package com.ecommerce.repository;

import com.ecommerce.model.CarrinhoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<CarrinhoModel, Long> {}
