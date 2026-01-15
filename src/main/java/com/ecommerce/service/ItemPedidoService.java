package com.ecommerce.service;

import com.ecommerce.dto.request.ItemPedidoRequestDto;
import com.ecommerce.dto.response.ItemPedidoResponseDto;
import com.ecommerce.model.ItemPedidoModel;
import com.ecommerce.model.PedidoModel;
import com.ecommerce.model.ProdutoModel;
import com.ecommerce.repository.ItemPedidoRepository;
import com.ecommerce.repository.PedidoRepository;
import com.ecommerce.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    @Transactional
    public ItemPedidoResponseDto adicionar(ItemPedidoRequestDto dto) {

        PedidoModel pedido = pedidoRepository.findById(dto.cdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + dto.cdPedido()));

        ProdutoModel produto = produtoRepository.findById(dto.cdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + dto.cdProduto()));

        if (!pedido.getStPedido().equals("PENDENTE")) {
            throw new RuntimeException("Não é possível adicionar itens a um pedido já confirmado");
        }

        ItemPedidoModel itemPedido = new ItemPedidoModel();

        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQtItem(dto.qtItem());
        itemPedido.setVlUnitario(dto.vlUnitario());
        itemPedido.setVlSubTotal(dto.vlUnitario() * dto.qtItem());

        ItemPedidoModel salvo = itemPedidoRepository.save(itemPedido);

        atualizarTotalPedido(pedido.getCdPedido());

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<ItemPedidoResponseDto> listar() {

        return itemPedidoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public ItemPedidoResponseDto buscarPorId(Long id) {

        ItemPedidoModel itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado com ID: " + id));

        return toResponseDto(itemPedido);

    }

    @Transactional
    public ItemPedidoResponseDto atualizar(Long id, ItemPedidoRequestDto dto) {

        ItemPedidoModel itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado com ID: " + id));

        if (!itemPedido.getPedido().getStPedido().equals("PENDENTE")) {
            throw new RuntimeException("Não é possível alterar itens de um pedido já confirmado");
        }

        itemPedido.setQtItem(dto.qtItem());
        itemPedido.setVlUnitario(dto.vlUnitario());
        itemPedido.setVlSubTotal(dto.vlUnitario() * dto.qtItem());

        ItemPedidoModel atualizado = itemPedidoRepository.save(itemPedido);

        atualizarTotalPedido(itemPedido.getPedido().getCdPedido());

        return toResponseDto(atualizado);

    }

    @Transactional
    public void remover(Long id) {

        ItemPedidoModel itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado com ID: " + id));

        if (!itemPedido.getPedido().getStPedido().equals("PENDENTE")) {
            throw new RuntimeException("Não é possível remover itens de um pedido já confirmado");
        }

        Long cdPedido = itemPedido.getPedido().getCdPedido();
        itemPedidoRepository.deleteById(id);

        atualizarTotalPedido(cdPedido);

    }

    @Transactional(readOnly = true)
    public Double calcularSubtotal(Long id) {

        ItemPedidoModel itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado com ID: " + id));

        return itemPedido.getVlSubTotal();

    }

    @Transactional
    public void atualizarQuantidade(Long id, Integer novaQuantidade) {

        ItemPedidoModel itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado com ID: " + id));

        if (!itemPedido.getPedido().getStPedido().equals("PENDENTE")) {
            throw new RuntimeException("Não é possível alterar a quantidade de itens de um pedido já confirmado");
        }

        if (novaQuantidade < 1) {
            throw new RuntimeException("A quantidade mínima é 1");
        }

        itemPedido.setQtItem(novaQuantidade);
        itemPedido.setVlSubTotal(itemPedido.getVlUnitario() * novaQuantidade);

        itemPedidoRepository.save(itemPedido);

        atualizarTotalPedido(itemPedido.getPedido().getCdPedido());

    }

    private void atualizarTotalPedido(Long cdPedido) {

        PedidoModel pedido = pedidoRepository.findById(cdPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + cdPedido));

        double totalItens = itemPedidoRepository.findAll().stream()
                .filter(item -> item.getPedido().getCdPedido().equals(cdPedido))
                .mapToDouble(ItemPedidoModel::getVlSubTotal)
                .sum();

        pedido.setVlTotal(totalItens + pedido.getVlFrete());
        pedidoRepository.save(pedido);

    }

    private ItemPedidoResponseDto toResponseDto(ItemPedidoModel itemPedido) {

        return new ItemPedidoResponseDto(
                itemPedido.getCdItemPedido(),
                new ItemPedidoResponseDto.PedidoSimplesDto(
                        itemPedido.getPedido().getCdPedido()
                ),
                new ItemPedidoResponseDto.ProdutoSimplesDto(
                        itemPedido.getProduto().getCdProduto(),
                        itemPedido.getProduto().getNmProduto()
                ),
                itemPedido.getQtItem(),
                itemPedido.getVlUnitario(),
                itemPedido.getVlSubTotal()
        );

    }

}