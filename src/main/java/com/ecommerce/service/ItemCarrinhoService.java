package com.ecommerce.service;

import com.ecommerce.dto.request.ItemCarrinhoRequestDto;
import com.ecommerce.dto.response.ItemCarrinhoResponseDto;
import com.ecommerce.model.CarrinhoModel;
import com.ecommerce.model.ItemCarrinhoModel;
import com.ecommerce.model.ProdutoModel;
import com.ecommerce.repository.CarrinhoRepository;
import com.ecommerce.repository.ItemCarrinhoRepository;
import com.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemCarrinhoService {

    private final ItemCarrinhoRepository itemCarrinhoRepository;

    private final CarrinhoRepository carrinhoRepository;

    private final ProdutoRepository produtoRepository;

    @Transactional
    public ItemCarrinhoResponseDto adicionar(ItemCarrinhoRequestDto dto) {

        CarrinhoModel carrinho = carrinhoRepository.findById(dto.cdCarrinho())
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com ID: " + dto.cdCarrinho()));

        ProdutoModel produto = produtoRepository.findById(dto.cdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + dto.cdProduto()));

        ItemCarrinhoModel itemExistente = itemCarrinhoRepository.findAll().stream()
                .filter(item -> item.getCarrinho().getCdCarrinho().equals(dto.cdCarrinho())
                        && item.getProduto().getCdProduto().equals(dto.cdProduto()))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setQtItem(itemExistente.getQtItem() + dto.qtItem());
            ItemCarrinhoModel atualizado = itemCarrinhoRepository.save(itemExistente);
            return toResponseDto(atualizado);
        }

        ItemCarrinhoModel itemCarrinho = new ItemCarrinhoModel();

        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(produto);
        itemCarrinho.setQtItem(dto.qtItem());
        itemCarrinho.setVlUnitario(produto.getVlProduto());

        ItemCarrinhoModel salvo = itemCarrinhoRepository.save(itemCarrinho);

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<ItemCarrinhoResponseDto> listar() {

        return itemCarrinhoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public ItemCarrinhoResponseDto buscarPorId(Long id) {

        ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado com ID: " + id));

        return toResponseDto(itemCarrinho);

    }

    @Transactional
    public ItemCarrinhoResponseDto atualizar(Long id, ItemCarrinhoRequestDto dto) {

        ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado com ID: " + id));

        itemCarrinho.setQtItem(dto.qtItem());

        ItemCarrinhoModel atualizado = itemCarrinhoRepository.save(itemCarrinho);

        return toResponseDto(atualizado);

    }

    @Transactional
    public void remover(Long id) {

        if (!itemCarrinhoRepository.existsById(id)) {
            throw new RuntimeException("Item do carrinho não encontrado com ID: " + id);
        }

        itemCarrinhoRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    public Double calcularSubtotal(Long id) {

        ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado com ID: " + id));

        return itemCarrinho.getVlUnitario() * itemCarrinho.getQtItem();

    }

    @Transactional
    public void atualizarQuantidade(Long id, Integer novaQuantidade) {

        ItemCarrinhoModel itemCarrinho = itemCarrinhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado com ID: " + id));

        if (novaQuantidade < 1) {
            throw new RuntimeException("A quantidade mínima é 1");
        }

        itemCarrinho.setQtItem(novaQuantidade);
        itemCarrinhoRepository.save(itemCarrinho);

    }

    private ItemCarrinhoResponseDto toResponseDto(ItemCarrinhoModel itemCarrinho) {

        return new ItemCarrinhoResponseDto(
                itemCarrinho.getCdItemCarrinho(),
                new ItemCarrinhoResponseDto.CarrinhoSimplesDto(
                        itemCarrinho.getCarrinho().getCdCarrinho()
                ),
                new ItemCarrinhoResponseDto.ProdutoSimplesDto(
                        itemCarrinho.getProduto().getCdProduto(),
                        itemCarrinho.getProduto().getNmProduto()
                ),
                itemCarrinho.getQtItem(),
                itemCarrinho.getVlUnitario()
        );

    }

}