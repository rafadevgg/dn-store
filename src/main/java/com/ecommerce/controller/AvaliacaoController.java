package com.ecommerce.controller;

import com.ecommerce.dto.request.AvaliacaoRequestDto;
import com.ecommerce.dto.response.AvaliacaoResponseDto;
import com.ecommerce.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDto> criar(@Valid @RequestBody AvaliacaoRequestDto dto) {

        AvaliacaoResponseDto response = avaliacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDto>> listar() {

        List<AvaliacaoResponseDto> avaliacoes = avaliacaoService.listar();
        return ResponseEntity.ok(avaliacoes);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> buscarPorId(@PathVariable Long id) {

        AvaliacaoResponseDto avaliacao = avaliacaoService.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDto> editar(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDto dto) {

        AvaliacaoResponseDto response = avaliacaoService.editar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        avaliacaoService.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/validar-nota/{nota}")
    public ResponseEntity<Void> validarNota(@PathVariable Integer nota) {

        avaliacaoService.validarNota(nota);
        return ResponseEntity.ok().build();

    }

}