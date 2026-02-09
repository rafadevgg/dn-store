package com.ecommerce.service;

import com.ecommerce.dto.request.CarrinhoRequestDto;
import com.ecommerce.dto.response.CarrinhoResponseDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.CarrinhoModel;
import com.ecommerce.model.UsuarioModel;
import com.ecommerce.repository.CarrinhoRepository;
import com.ecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CarrinhoResponseDto adicionar(CarrinhoRequestDto dto) {

        UsuarioModel usuario = usuarioRepository.findById(dto.cdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "ID", dto.cdUsuario()));

        CarrinhoModel carrinho = new CarrinhoModel();

        carrinho.setUsuario(usuario);

        CarrinhoModel salvo = carrinhoRepository.save(carrinho);

        return toResponseDto(salvo);

    }

    @Transactional(readOnly = true)
    public List<CarrinhoResponseDto> listar() {

        return carrinhoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public CarrinhoResponseDto buscarPorId(Long id) {

        CarrinhoModel carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho", "ID", id));

        return toResponseDto(carrinho);

    }

    @Transactional
    public void remover(Long id) {

        if (!carrinhoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Carrinho", "ID", id);
        }

        carrinhoRepository.deleteById(id);

    }

    @Transactional
    public void limpar(Long id) {

        CarrinhoModel carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho", "ID", id));

        carrinhoRepository.save(carrinho);

    }

    @Transactional(readOnly = true)
    public Double calcularTotal(Long id) {

        CarrinhoModel carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho", "ID", id));

        return 0.0;

    }

    private CarrinhoResponseDto toResponseDto(CarrinhoModel carrinho) {

        return new CarrinhoResponseDto(
                carrinho.getCdCarrinho(),
                new CarrinhoResponseDto.UsuarioSimplesDto(
                        carrinho.getUsuario().getCdUsuario(),
                        carrinho.getUsuario().getNmUsuario()
                ),
                carrinho.getDtCriacao(),
                carrinho.getDtAtualizacao()
        );

    }

}