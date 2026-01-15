package com.ecommerce.service;

import com.ecommerce.dto.request.AvaliacaoRequestDto;
import com.ecommerce.dto.response.AvaliacaoResponseDto;
import com.ecommerce.model.AvaliacaoModel;
import com.ecommerce.model.ProdutoModel;
import com.ecommerce.model.UsuarioModel;
import com.ecommerce.repository.AvaliacaoRepository;
import com.ecommerce.repository.ProdutoRepository;
import com.ecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final ProdutoRepository produtoRepository;

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public AvaliacaoResponseDto criar(AvaliacaoRequestDto dto) {

        ProdutoModel produto = produtoRepository.findById(dto.cdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + dto.cdProduto()));

        UsuarioModel usuario = usuarioRepository.findById(dto.cdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.cdUsuario()));

        validarNota(dto.nrNota());

        AvaliacaoModel avaliacao = new AvaliacaoModel();

        avaliacao.setProduto(produto);
        avaliacao.setUsuario(usuario);
        avaliacao.setNrNota(dto.nrNota());
        avaliacao.setDsComentario(dto.dsComentario());

        AvaliacaoModel salva = avaliacaoRepository.save(avaliacao);

        return toResponseDto(salva);

    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDto> listar() {

        return avaliacaoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public AvaliacaoResponseDto buscarPorId(Long id) {

        AvaliacaoModel avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com ID: " + id));

        return toResponseDto(avaliacao);

    }

    @Transactional
    public AvaliacaoResponseDto editar(Long id, AvaliacaoRequestDto dto) {

        AvaliacaoModel avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com ID: " + id));

        validarNota(dto.nrNota());

        avaliacao.setNrNota(dto.nrNota());
        avaliacao.setDsComentario(dto.dsComentario());

        AvaliacaoModel atualizada = avaliacaoRepository.save(avaliacao);

        return toResponseDto(atualizada);

    }

    @Transactional
    public void deletar(Long id) {

        if (!avaliacaoRepository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada com ID: " + id);
        }

        avaliacaoRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    public void validarNota(Integer nota) {

        if (nota < 1 || nota > 5) {
            throw new RuntimeException("A nota deve estar entre 1 e 5. Nota informada: " + nota);
        }

    }

    private AvaliacaoResponseDto toResponseDto(AvaliacaoModel avaliacao) {

        return new AvaliacaoResponseDto(
                avaliacao.getCdAvaliacao(),
                new AvaliacaoResponseDto.ProdutoSimplesDto(
                        avaliacao.getProduto().getCdProduto(),
                        avaliacao.getProduto().getNmProduto()
                ),
                new AvaliacaoResponseDto.UsuarioSimplesDto(
                        avaliacao.getUsuario().getCdUsuario(),
                        avaliacao.getUsuario().getNmUsuario()
                ),
                avaliacao.getNrNota(),
                avaliacao.getDsComentario(),
                avaliacao.getDtAvaliacao()
        );

    }

}