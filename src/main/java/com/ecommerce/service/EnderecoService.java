package com.ecommerce.service;

import com.ecommerce.dto.request.EnderecoRequestDto;
import com.ecommerce.dto.response.EnderecoResponseDto;
import com.ecommerce.model.EnderecoModel;
import com.ecommerce.model.UsuarioModel;
import com.ecommerce.repository.EnderecoRepository;
import com.ecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public EnderecoResponseDto criar(EnderecoRequestDto dto) {

        UsuarioModel usuario = usuarioRepository.findById(dto.cdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.cdUsuario()));

        EnderecoModel endereco = new EnderecoModel();

        endereco.setUsuario(usuario);
        endereco.setDsLogradouro(dto.dsLogradouro());
        endereco.setDsNumero(dto.dsNumero());
        endereco.setDsComplemento(dto.dsComplemento());
        endereco.setDsBairro(dto.dsBairro());
        endereco.setDsCidade(dto.dsCidade());
        endereco.setDsEstado(dto.dsEstado());
        endereco.setDsCep(dto.dsCep());
        endereco.setStPadrao(dto.stPadrao() != null ? dto.stPadrao() : false);

        EnderecoModel salvo = enderecoRepository.save(endereco);

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<EnderecoResponseDto> listar() {

        return enderecoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public EnderecoResponseDto buscarPorId(Long id) {

        EnderecoModel endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + id));

        return toResponseDto(endereco);

    }

    @Transactional
    public EnderecoResponseDto atualizar(Long id, EnderecoRequestDto dto) {

        EnderecoModel endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + id));

        endereco.setDsLogradouro(dto.dsLogradouro());
        endereco.setDsNumero(dto.dsNumero());
        endereco.setDsComplemento(dto.dsComplemento());
        endereco.setDsBairro(dto.dsBairro());
        endereco.setDsCidade(dto.dsCidade());
        endereco.setDsEstado(dto.dsEstado());
        endereco.setDsCep(dto.dsCep());

        if (dto.stPadrao() != null) {
            endereco.setStPadrao(dto.stPadrao());
        }

        EnderecoModel atualizado = enderecoRepository.save(endereco);

        return toResponseDto(atualizado);

    }

    @Transactional
    public void deletar(Long id) {

        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado com ID: " + id);
        }

        enderecoRepository.deleteById(id);

    }

    @Transactional
    public void definirPadrao(Long id) {

        EnderecoModel endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + id));

        enderecoRepository.findAll().stream()
                .filter(e -> e.getUsuario().getCdUsuario().equals(endereco.getUsuario().getCdUsuario()))
                .forEach(e -> {
                    e.setStPadrao(false);
                    enderecoRepository.save(e);
                });

        endereco.setStPadrao(true);
        enderecoRepository.save(endereco);

    }

    @Transactional(readOnly = true)
    public EnderecoResponseDto validarCep(String cep) {

        if (!cep.matches("\\d{5}-?\\d{3}")) {
            throw new RuntimeException("CEP inválido: " + cep);
        }

        return null;

    }

    private EnderecoResponseDto toResponseDto(EnderecoModel endereco) {

        return new EnderecoResponseDto(
                endereco.getCdEndereco(),
                new EnderecoResponseDto.UsuarioSimplesDto(
                        endereco.getUsuario().getCdUsuario(),
                        endereco.getUsuario().getNmUsuario()
                ),
                endereco.getDsLogradouro(),
                endereco.getDsNumero(),
                endereco.getDsComplemento(),
                endereco.getDsBairro(),
                endereco.getDsCidade(),
                endereco.getDsEstado(),
                endereco.getDsCep(),
                endereco.getStPadrao()
        );

    }

}