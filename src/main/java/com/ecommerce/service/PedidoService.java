package com.ecommerce.service;

import com.ecommerce.dto.request.PedidoRequestDto;
import com.ecommerce.dto.response.PedidoResponseDto;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final UsuarioRepository usuarioRepository;

    private final EnderecoRepository enderecoRepository;

    private final ProdutoRepository produtoRepository;

    private final ItemPedidoRepository itemPedidoRepository;

    @Transactional
    public PedidoResponseDto criar(PedidoRequestDto dto) {

        UsuarioModel usuario = usuarioRepository.findById(dto.cdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.cdUsuario()));

        EnderecoModel endereco = enderecoRepository.findById(dto.cdEndereco())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + dto.cdEndereco()));

        PedidoModel pedido = new PedidoModel();

        pedido.setUsuario(usuario);
        pedido.setEndereco(endereco);
        pedido.setNrPedido(gerarNumeroPedido());
        pedido.setVlFrete(dto.vlFrete());
        pedido.setTpPagamento(dto.tpPagamento());
        pedido.setStPedido("PENDENTE");

        double totalItens = 0.0;
        for (PedidoRequestDto.ItemPedidoRequest item : dto.itens()) {
            ProdutoModel produto = produtoRepository.findById(item.cdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + item.cdProduto()));
            totalItens += produto.getVlProduto() * item.qtItem();
        }

        pedido.setVlTotal(totalItens + dto.vlFrete());

        PedidoModel salvo = pedidoRepository.save(pedido);

        for (PedidoRequestDto.ItemPedidoRequest item : dto.itens()) {
            ProdutoModel produto = produtoRepository.findById(item.cdProduto())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + item.cdProduto()));

            ItemPedidoModel itemPedido = new ItemPedidoModel();
            itemPedido.setPedido(salvo);
            itemPedido.setProduto(produto);
            itemPedido.setQtItem(item.qtItem());
            itemPedido.setVlUnitario(produto.getVlProduto());
            itemPedido.setVlSubTotal(produto.getVlProduto() * item.qtItem());

            itemPedidoRepository.save(itemPedido);
        }

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listar() {

        return pedidoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public PedidoResponseDto buscarPorId(Long id) {

        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        return toResponseDto(pedido);

    }

    @Transactional
    public void confirmar(Long id) {

        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (!pedido.getStPedido().equals("PENDENTE")) {
            throw new RuntimeException("Apenas pedidos pendentes podem ser confirmados");
        }

        pedido.setStPedido("CONFIRMADO");
        pedidoRepository.save(pedido);

    }

    @Transactional
    public void cancelar(Long id) {

        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        if (pedido.getStPedido().equals("ENTREGUE") || pedido.getStPedido().equals("CANCELADO")) {
            throw new RuntimeException("Pedido não pode ser cancelado no status atual: " + pedido.getStPedido());
        }

        pedido.setStPedido("CANCELADO");
        pedidoRepository.save(pedido);

    }

    @Transactional(readOnly = true)
    public Double calcularTotal(Long id) {

        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        return pedido.getVlTotal();

    }

    @Transactional
    public void atualizarStatus(Long id, String novoStatus) {

        PedidoModel pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));

        List<String> statusPermitidos = List.of("PENDENTE", "CONFIRMADO", "PROCESSANDO", "ENVIADO", "ENTREGUE", "CANCELADO");

        if (!statusPermitidos.contains(novoStatus)) {
            throw new RuntimeException("Status inválido: " + novoStatus);
        }

        pedido.setStPedido(novoStatus);
        pedidoRepository.save(pedido);

    }

    private String gerarNumeroPedido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "PED" + LocalDateTime.now().format(formatter);
    }

    private PedidoResponseDto toResponseDto(PedidoModel pedido) {

        return new PedidoResponseDto(
                pedido.getCdPedido(),
                new PedidoResponseDto.UsuarioSimplesDto(
                        pedido.getUsuario().getCdUsuario(),
                        pedido.getUsuario().getNmUsuario()
                ),
                new PedidoResponseDto.EnderecoSimplesDto(
                        pedido.getEndereco().getCdEndereco(),
                        pedido.getEndereco().getDsCep()
                ),
                pedido.getNrPedido(),
                pedido.getVlTotal(),
                pedido.getVlFrete(),
                pedido.getTpPagamento(),
                pedido.getStPedido(),
                pedido.getDtPedido(),
                pedido.getDtAtualizacao()
        );

    }

}
