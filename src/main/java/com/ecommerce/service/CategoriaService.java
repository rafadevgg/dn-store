package com.ecommerce.service;

import com.ecommerce.dto.request.CategoriaRequestDto;
import com.ecommerce.dto.response.CategoriaResponseDto;
import com.ecommerce.model.CategoriaModel;
import com.ecommerce.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDto criar(CategoriaRequestDto dto) {

        CategoriaModel categoria = new CategoriaModel();

        categoria.setNmCategoria(dto.nmCategoria());
        categoria.setDsCategoria(dto.dsCategoria());
        categoria.setDsSlug(dto.dsSlug());

        CategoriaModel salva = categoriaRepository.save(categoria);

        return toResponseDto(salva);

    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> listarProdutos() {

        return categoriaRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public CategoriaResponseDto buscarPorId(Long id) {

        CategoriaModel categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));

        return toResponseDto(categoria);

    }

    @Transactional
    public CategoriaResponseDto atualizar(Long id, CategoriaRequestDto dto) {

        CategoriaModel categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));

        categoria.setNmCategoria(dto.nmCategoria());
        categoria.setDsCategoria(dto.dsCategoria());
        categoria.setDsSlug(dto.dsSlug());

        CategoriaModel atualizada = categoriaRepository.save(categoria);

        return toResponseDto(atualizada);

    }

    @Transactional
    public void deletar(Long id) {

        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada com ID: " + id);
        }

        categoriaRepository.deleteById(id);

    }

    private CategoriaResponseDto toResponseDto(CategoriaModel categoria) {

        return new CategoriaResponseDto(
                categoria.getCdCategoria(),
                categoria.getNmCategoria(),
                categoria.getDsCategoria(),
                categoria.getDsSlug()
        );

    }

}