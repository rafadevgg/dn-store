package com.ecommerce.controller;

import com.ecommerce.dto.request.ItemPedidoRequestDto;
import com.ecommerce.dto.response.ItemPedidoResponseDto;
import com.ecommerce.service.ItemPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @PostMapping
    public ResponseEntity<ItemPedidoResponseDto> adicionar(@Valid @RequestBody ItemPedidoRequestDto dto) {

        ItemPedidoResponseDto response = itemPedidoService.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponseDto>> listar() {

        List<ItemPedidoResponseDto> itens = itemPedidoService.listar();
        return ResponseEntity.ok(itens);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDto> buscarPorId(@PathVariable Long id) {

        ItemPedidoResponseDto item = itemPedidoService.buscarPorId(id);
        return ResponseEntity.ok(item);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ItemPedidoRequestDto dto) {

        ItemPedidoResponseDto response = itemPedidoService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        itemPedidoService.remover(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}/subtotal")
    public ResponseEntity<Double> calcularSubtotal(@PathVariable Long id) {

        Double subtotal = itemPedidoService.calcularSubtotal(id);
        return ResponseEntity.ok(subtotal);

    }

    @PatchMapping("/{id}/quantidade")
    public ResponseEntity<Void> atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam Integer novaQuantidade) {

        itemPedidoService.atualizarQuantidade(id, novaQuantidade);
        return ResponseEntity.noContent().build();

    }

}