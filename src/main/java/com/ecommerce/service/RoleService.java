package com.ecommerce.service;

import com.ecommerce.dto.request.RoleRequestDto;
import com.ecommerce.dto.response.RoleResponseDto;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.RoleModel;
import com.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponseDto criar(RoleRequestDto dto) {

        boolean roleExiste = roleRepository.findAll().stream()
                .anyMatch(role -> role.getNmRole().equals(dto.nmRole()));

        if (roleExiste) {
            throw new BusinessException("Role já existe: " + dto.nmRole());
        }

        RoleModel role = new RoleModel();
        role.setNmRole(dto.nmRole());

        RoleModel salva = roleRepository.save(role);

        return toResponseDto(salva);

    }

    @Transactional(readOnly = true)
    public List<RoleResponseDto> listar() {

        return roleRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public RoleResponseDto buscarPorId(Long id) {

        RoleModel role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada com ID: " + id));

        return toResponseDto(role);

    }

    @Transactional
    public RoleResponseDto atualizar(Long id, RoleRequestDto dto) {

        RoleModel role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "ID", id));

        boolean nomeJaExiste = roleRepository.findAll().stream()
                .anyMatch(r -> r.getNmRole().equals(dto.nmRole()) && !r.getCdRole().equals(id));

        if (nomeJaExiste) {
            throw new BusinessException("Já existe uma role com este nome: " + dto.nmRole());
        }

        role.setNmRole(dto.nmRole());

        RoleModel atualizada = roleRepository.save(role);

        return toResponseDto(atualizada);

    }

    @Transactional
    public void deletar(Long id) {

        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role não encontrada com ID: " + id);
        }

        roleRepository.deleteById(id);

    }

    private RoleResponseDto toResponseDto(RoleModel role) {

        return new RoleResponseDto(
                role.getCdRole(),
                role.getNmRole()
        );

    }

}