package com.ecommerce.controller;

import com.ecommerce.dto.request.CarrinhoRequestDto;
import com.ecommerce.dto.response.CarrinhoResponseDto;
import com.ecommerce.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrinhos")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping
    public ResponseEntity<CarrinhoResponseDto> adicionar(@Valid @RequestBody CarrinhoRequestDto dto) {

        CarrinhoResponseDto response = carrinhoService.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<CarrinhoResponseDto>> listar() {

        List<CarrinhoResponseDto> carrinhos = carrinhoService.listar();
        return ResponseEntity.ok(carrinhos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoResponseDto> buscarPorId(@PathVariable Long id) {

        CarrinhoResponseDto carrinho = carrinhoService.buscarPorId(id);
        return ResponseEntity.ok(carrinho);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        carrinhoService.remover(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/limpar")
    public ResponseEntity<Void> limpar(@PathVariable Long id) {

        carrinhoService.limpar(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Double> calcularTotal(@PathVariable Long id) {

        Double total = carrinhoService.calcularTotal(id);
        return ResponseEntity.ok(total);

    }

}