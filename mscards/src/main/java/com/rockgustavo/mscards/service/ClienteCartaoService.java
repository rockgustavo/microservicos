package com.rockgustavo.mscards.service;

import java.util.List;

import com.rockgustavo.mscards.rest.dto.ClienteCartaoDTO;

public interface ClienteCartaoService {

    List<ClienteCartaoDTO> listCartoesByCpf(String cpf);
}
