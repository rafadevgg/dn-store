package com.ecommerce.controller;

import com.ecommerce.dto.request.PedidoRequestDto;
import com.ecommerce.dto.response.PedidoResponseDto;
import com.ecommerce.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criar(@Valid @RequestBody PedidoRequestDto dto) {

        PedidoResponseDto response = pedidoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> listar() {

        List<PedidoResponseDto> pedidos = pedidoService.listar();
        return ResponseEntity.ok(pedidos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPorId(@PathVariable Long id) {

        PedidoResponseDto pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);

    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmar(@PathVariable Long id) {

        pedidoService.confirmar(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {

        pedidoService.cancelar(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Double> calcularTotal(@PathVariable Long id) {

        Double total = pedidoService.calcularTotal(id);
        return ResponseEntity.ok(total);

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String novoStatus) {

        pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.noContent().build();

    }

}