package com.ecommerce.controller;

import com.ecommerce.dto.request.UsuarioRequestDto;
import com.ecommerce.dto.response.UsuarioResponseDto;
import com.ecommerce.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@Valid @RequestBody UsuarioRequestDto dto) {

        UsuarioResponseDto response = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {

        List<UsuarioResponseDto> usuarios = usuarioService.listarProdutos();
        return ResponseEntity.ok(usuarios);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {

        UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDto dto) {

        UsuarioResponseDto response = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Long id,
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha) {

        usuarioService.alterarSenha(id, senhaAtual, novaSenha);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/validar-cpf/{cpf}")
    public ResponseEntity<Boolean> validarCpf(@PathVariable String cpf) {

        boolean cpfDisponivel = usuarioService.validarCpf(cpf);
        return ResponseEntity.ok(cpfDisponivel);

    }

    @PostMapping("/autenticar")
    public ResponseEntity<Boolean> autenticar(
            @RequestParam String email,
            @RequestParam String senha) {

        boolean autenticado = usuarioService.autenticar(email, senha);
        return ResponseEntity.ok(autenticado);

    }

}