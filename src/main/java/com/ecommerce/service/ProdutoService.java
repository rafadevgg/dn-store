package com.ecommerce.service;

import com.ecommerce.dto.request.ProdutoRequestDto;
import com.ecommerce.dto.response.ProdutoResponseDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.CategoriaModel;
import com.ecommerce.model.ProdutoModel;
import com.ecommerce.repository.CategoriaRepository;
import com.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public ProdutoResponseDto criar(ProdutoRequestDto dto) {

        CategoriaModel categoria = categoriaRepository.findById(dto.cdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "ID", dto.cdCategoria()));

        ProdutoModel produto = new ProdutoModel();

        produto.setCategoria(categoria);
        produto.setNmProduto(dto.nmProduto());
        produto.setDsProduto(dto.dsProduto());
        produto.setDsSlug(dto.dsSlug());
        produto.setVlProduto(dto.vlProduto());
        produto.setDsImagemUrl(dto.dsImagemUrl());
        produto.setStAtivo(dto.stAtivo() != null ? dto.stAtivo() : true);

        ProdutoModel salvo = produtoRepository.save(produto);

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDto> listarProdutos() {

        return produtoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public ProdutoResponseDto buscarPorId(Long id) {

        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "ID", id));

        return toResponseDto(produto);

    }

    @Transactional
    public ProdutoResponseDto atualizar(Long id, ProdutoRequestDto dto) {

        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "ID", id));

        CategoriaModel categoria = categoriaRepository.findById(dto.cdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", "ID", dto.cdCategoria()));

        produto.setCategoria(categoria);
        produto.setNmProduto(dto.nmProduto());
        produto.setDsProduto(dto.dsProduto());
        produto.setDsSlug(dto.dsSlug());
        produto.setVlProduto(dto.vlProduto());
        produto.setDsImagemUrl(dto.dsImagemUrl());

        if (dto.stAtivo() != null) {
            produto.setStAtivo(dto.stAtivo());
        }

        ProdutoModel atualizado = produtoRepository.save(produto);

        return toResponseDto(atualizado);

    }

    @Transactional
    public void deletar(Long id) {

        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto", "ID", id);
        }

        produtoRepository.deleteById(id);

    }

    @Transactional
    public void ativar(Long id) {

        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "ID", id));

        produto.setStAtivo(true);
        produtoRepository.save(produto);

    }

    @Transactional
    public void desativar(Long id) {

        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "ID", id));

        produto.setStAtivo(false);
        produtoRepository.save(produto);

    }

    @Transactional
    public Double calcularPrecoPromocional(Long id, Double percentualDesconto) {

        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "ID", id));

        return produto.getVlProduto() * (1 - percentualDesconto / 100);

    }

    private ProdutoResponseDto toResponseDto(ProdutoModel produto) {

        return new ProdutoResponseDto(
                produto.getCdProduto(),
                new ProdutoResponseDto.CategoriaSimplesDto(
                        produto.getCategoria().getCdCategoria(),
                        produto.getCategoria().getNmCategoria()
                ),
                produto.getNmProduto(),
                produto.getDsProduto(),
                produto.getDsSlug(),
                produto.getVlProduto(),
                produto.getDsImagemUrl(),
                produto.getStAtivo(),
                produto.getDtCriacao(),
                produto.getDtAtualizacao()
        );

    }
}