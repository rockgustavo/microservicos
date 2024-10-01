package com.rockgustavo.mscards.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rockgustavo.mscards.model.entity.Cartao;
import com.rockgustavo.mscards.model.repository.CartaoRepository;
import com.rockgustavo.mscards.rest.dto.CartaoDTO;
import com.rockgustavo.mscards.service.CartaoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public CartaoDTO save(Cartao cartao) {
        return modelMapper.map(cartaoRepository.save(cartao), CartaoDTO.class);

    }

    public Optional<CartaoDTO> getById(Long id) {
        return cartaoRepository.findById(id)
                .map(cartao -> modelMapper.map(cartao, CartaoDTO.class));
    }

    public List<CartaoDTO> getCartoesRendaMenorIgual(Long renda) {
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal)
                .stream()
                .map(cartao -> modelMapper.map(cartao, CartaoDTO.class))
                .collect(Collectors.toList());
    }
}