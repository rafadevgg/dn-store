package com.ecommerce.service;

import com.ecommerce.dto.request.UsuarioRequestDto;
import com.ecommerce.dto.response.UsuarioResponseDto;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.UsuarioModel;
import com.ecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDto criar(UsuarioRequestDto dto) {

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNmUsuario(dto.nmUsuario());
        usuario.setDsEmail(dto.dsEmail());
        usuario.setDsSenha(passwordEncoder.encode(dto.dsSenha()));
        usuario.setDsCpf(dto.dsCpf());
        usuario.setDsTelefone(dto.dsTelefone());

        UsuarioModel salvo = usuarioRepository.save(usuario);

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listarProdutos() {

        return usuarioRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto buscarPorId(Long id) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "ID", id));

        return toResponseDto(usuario);

    }

    @Transactional
    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto dto) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "ID", id));

        usuario.setNmUsuario(dto.nmUsuario());
        usuario.setDsEmail(dto.dsEmail());

        if (dto.dsSenha() != null && !dto.dsSenha().isBlank()) {
            usuario.setDsSenha(passwordEncoder.encode(dto.dsSenha()));
        }

        usuario.setDsCpf(dto.dsCpf());
        usuario.setDsTelefone(dto.dsTelefone());

        UsuarioModel atualizado = usuarioRepository.save(usuario);

        return toResponseDto(atualizado);

    }

    @Transactional
    public void deletar(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário", "ID", id);
        }

        usuarioRepository.deleteById(id);

    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "ID", id));

        if (!passwordEncoder.matches(senhaAtual, usuario.getDsSenha())) {
            throw new BusinessException("Senha atual incorreta");
        }

        usuario.setDsSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

    }

    @Transactional(readOnly = true)
    public boolean validarCpf(String cpf) {

        return usuarioRepository.findAll().stream()
                .noneMatch(u -> u.getDsCpf().equals(cpf));

    }

    public boolean autenticar(String email, String senha) {

        UsuarioModel usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getDsEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            return false;
        }

        return passwordEncoder.matches(senha, usuario.getDsSenha());

    }

    private UsuarioResponseDto toResponseDto(UsuarioModel usuario) {

        return new UsuarioResponseDto(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getDsEmail(),
                usuario.getDsCpf(),
                usuario.getDsTelefone(),
                usuario.getDtCriacao(),
                usuario.getDtAtualizacao()
        );

    }
}