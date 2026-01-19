package com.ecommerce.controller;

import com.ecommerce.dto.request.ProdutoRequestDto;
import com.ecommerce.dto.response.ProdutoResponseDto;
import com.ecommerce.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> criar(@Valid @RequestBody ProdutoRequestDto dto) {

        ProdutoResponseDto response = produtoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listar() {

        List<ProdutoResponseDto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable Long id) {

        ProdutoResponseDto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDto dto) {

        ProdutoResponseDto response = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        produtoService.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {

        produtoService.ativar(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {

        produtoService.desativar(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}/preco-promocional")
    public ResponseEntity<Double> calcularPrecoPromocional(
            @PathVariable Long id,
            @RequestParam Double percentualDesconto) {

        Double precoPromocional = produtoService.calcularPrecoPromocional(id, percentualDesconto);
        return ResponseEntity.ok(precoPromocional);

    }

}