package com.ecommerce.controller;

import com.ecommerce.dto.request.EstoqueRequestDto;
import com.ecommerce.dto.response.EstoqueResponseDto;
import com.ecommerce.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<EstoqueResponseDto> adicionar(@Valid @RequestBody EstoqueRequestDto dto) {

        EstoqueResponseDto response = estoqueService.adicionar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponseDto>> listar() {

        List<EstoqueResponseDto> estoques = estoqueService.listar();
        return ResponseEntity.ok(estoques);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDto> buscarPorId(@PathVariable Long id) {

        EstoqueResponseDto estoque = estoqueService.buscarPorId(id);
        return ResponseEntity.ok(estoque);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstoqueRequestDto dto) {

        EstoqueResponseDto response = estoqueService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {

        estoqueService.remover(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/reservar")
    public ResponseEntity<Void> reservar(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {

        estoqueService.reservar(id, quantidade);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/liberar-reserva")
    public ResponseEntity<Void> liberarReserva(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {

        estoqueService.liberarReserva(id, quantidade);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}/verificar-disponibilidade")
    public ResponseEntity<Boolean> verificarDisponibilidade(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {

        boolean disponivel = estoqueService.verificarDisponibilidade(id, quantidade);
        return ResponseEntity.ok(disponivel);

    }

}