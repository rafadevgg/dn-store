package com.ecommerce.controller;

import com.ecommerce.dto.request.RoleRequestDto;
import com.ecommerce.dto.response.RoleResponseDto;
import com.ecommerce.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDto> criar(@Valid @RequestBody RoleRequestDto dto) {

        RoleResponseDto response = roleService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> listar() {

        List<RoleResponseDto> roles = roleService.listar();
        return ResponseEntity.ok(roles);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> buscarPorId(@PathVariable Long id) {

        RoleResponseDto role = roleService.buscarPorId(id);
        return ResponseEntity.ok(role);

    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RoleRequestDto dto) {

        RoleResponseDto response = roleService.atualizar(id, dto);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        roleService.deletar(id);
        return ResponseEntity.noContent().build();

    }

}