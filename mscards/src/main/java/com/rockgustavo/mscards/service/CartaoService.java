package com.rockgustavo.mscards.service;

import java.util.List;
import java.util.Optional;

import com.rockgustavo.mscards.model.entity.Cartao;
import com.rockgustavo.mscards.rest.dto.CartaoDTO;

public interface CartaoService {

    CartaoDTO save(Cartao cartao);

    List<CartaoDTO> getCartoesRendaMenorIgual(Long renda);

    Optional<CartaoDTO> getById(Long id);

}
