package com.ecommerce.controller;

import com.ecommerce.dto.request.ItemCarrinhoRequestDto;
import com.ecommerce.dto.response.ItemCarrinhoResponseDto;
import com.ecommerce.service.ItemCarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-carrinho")
@RequiredArgsConstructor
public class ItemCarrinhoController {

    private final ItemCarrinhoService itemCarrinhoService;

    @PostMapping
    public ResponseEntity<ItemCarrinhoResponseDto> adicionar(@Valid @RequestBody ItemCarrinhoRequestDto dto) {

        ItemCarrinhoResponseDto response = itemCarrinhoService.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<ItemCarrinhoResponseDto>> listar() {

        List<ItemCarrinhoResponseDto> itens = itemCarrinhoService.listar();
        return ResponseEntity.ok(itens);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCarrinhoResponseDto> buscarPorId(@PathVariable Long id) {

        ItemCarrinhoResponseDto item = itemCarrinhoService.buscarPorId(id);
        return ResponseEntity.ok(item);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCarrinhoResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemCarrinhoRequestDto dto) {

        ItemCarrinhoResponseDto response = itemCarrinhoService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        itemCarrinhoService.remover(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}/subtotal")
    public ResponseEntity<Double> calcularSubtotal(@PathVariable Long id) {

        Double subtotal = itemCarrinhoService.calcularSubtotal(id);
        return ResponseEntity.ok(subtotal);

    }

    @PatchMapping("/{id}/quantidade")
    public ResponseEntity<Void> atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam Integer novaQuantidade) {

        itemCarrinhoService.atualizarQuantidade(id, novaQuantidade);
        return ResponseEntity.noContent().build();

    }

}
