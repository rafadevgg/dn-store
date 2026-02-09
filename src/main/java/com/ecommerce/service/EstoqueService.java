package com.ecommerce.service;

import com.ecommerce.dto.request.EstoqueRequestDto;
import com.ecommerce.dto.response.EstoqueResponseDto;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.EstoqueModel;
import com.ecommerce.model.ProdutoModel;
import com.ecommerce.repository.EstoqueRepository;
import com.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    private final ProdutoRepository produtoRepository;

    @Transactional
    public EstoqueResponseDto adicionar(EstoqueRequestDto dto) {

        ProdutoModel produto = produtoRepository.findById(dto.cdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + dto.cdProduto()));

        EstoqueModel estoque = new EstoqueModel();

        estoque.setProduto(produto);
        estoque.setQtEstoque(dto.qtEstoque());
        estoque.setQtReservado(dto.qtReservado() != null ? dto.qtReservado() : 0);

        EstoqueModel salvo = estoqueRepository.save(estoque);

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<EstoqueResponseDto> listar() {

        return estoqueRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public EstoqueResponseDto buscarPorId(Long id) {

        EstoqueModel estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com ID: " + id));

        return toResponseDto(estoque);

    }

    @Transactional
    public EstoqueResponseDto atualizar(Long id, EstoqueRequestDto dto) {

        EstoqueModel estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com ID: " + id));

        estoque.setQtEstoque(dto.qtEstoque());

        if (dto.qtReservado() != null) {
            estoque.setQtReservado(dto.qtReservado());
        }

        EstoqueModel atualizado = estoqueRepository.save(estoque);

        return toResponseDto(atualizado);

    }

    @Transactional
    public void remover(Long id) {

        if (!estoqueRepository.existsById(id)) {
            throw new RuntimeException("Estoque não encontrado com ID: " + id);
        }

        estoqueRepository.deleteById(id);

    }

    @Transactional
    public void reservar(Long id, Integer quantidade) {

        EstoqueModel estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque", "ID", id));

        int disponiveis = estoque.getQtEstoque() - estoque.getQtReservado();

        if (disponiveis < quantidade) {
            throw new BusinessException("Quantidade insuficiente em estoque. Disponível: " + disponiveis);
        }

        estoque.setQtReservado(estoque.getQtReservado() + quantidade);
        estoqueRepository.save(estoque);

    }

    @Transactional
    public void liberarReserva(Long id, Integer quantidade) {

        EstoqueModel estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque", "ID", id));

        int novaQuantidadeReservada = estoque.getQtReservado() - quantidade;

        if (novaQuantidadeReservada < 0) {
            throw new BusinessException("Quantidade a liberar maior que quantidade reservada");
        }

        estoque.setQtReservado(novaQuantidadeReservada);
        estoqueRepository.save(estoque);

    }

    @Transactional(readOnly = true)
    public boolean verificarDisponibilidade(Long id, Integer quantidade) {

        EstoqueModel estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado com ID: " + id));

        int disponiveis = estoque.getQtEstoque() - estoque.getQtReservado();

        return disponiveis >= quantidade;

    }

    private EstoqueResponseDto toResponseDto(EstoqueModel estoque) {

        return new EstoqueResponseDto(
                estoque.getCdEstoque(),
                new EstoqueResponseDto.ProdutoSimplesDto(
                        estoque.getProduto().getCdProduto(),
                        estoque.getProduto().getNmProduto()
                ),
                estoque.getQtEstoque(),
                estoque.getQtReservado(),
                estoque.getDtAtualizacao()
        );

    }
}