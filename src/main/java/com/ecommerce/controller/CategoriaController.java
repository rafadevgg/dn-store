package com.ecommerce.controller;

import com.ecommerce.dto.request.CategoriaRequestDto;
import com.ecommerce.dto.response.CategoriaResponseDto;
import com.ecommerce.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> criar(@Valid @RequestBody CategoriaRequestDto dto) {

        CategoriaResponseDto response = categoriaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar() {

        List<CategoriaResponseDto> categorias = categoriaService.listarProdutos();
        return ResponseEntity.ok(categorias);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Long id) {

        CategoriaResponseDto categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDto dto) {

        CategoriaResponseDto response = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();

    }

}