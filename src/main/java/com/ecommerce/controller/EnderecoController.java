package com.ecommerce.controller;

import com.ecommerce.dto.request.EnderecoRequestDto;
import com.ecommerce.dto.response.EnderecoResponseDto;
import com.ecommerce.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<EnderecoResponseDto> criar(@Valid @RequestBody EnderecoRequestDto dto) {

        EnderecoResponseDto response = enderecoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDto>> listar() {

        List<EnderecoResponseDto> enderecos = enderecoService.listar();
        return ResponseEntity.ok(enderecos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> buscarPorId(@PathVariable Long id) {

        EnderecoResponseDto endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(endereco);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EnderecoRequestDto dto) {

        EnderecoResponseDto response = enderecoService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/definir-padrao")
    public ResponseEntity<Void> definirPadrao(@PathVariable Long id) {

        enderecoService.definirPadrao(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/validar-cep/{cep}")
    public ResponseEntity<EnderecoResponseDto> validarCep(@PathVariable String cep) {

        EnderecoResponseDto response = enderecoService.validarCep(cep);
        return ResponseEntity.ok(response);

    }

}