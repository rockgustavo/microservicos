package com.rockgustavo.mscards.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rockgustavo.mscards.model.repository.ClienteCartaoRepository;
import com.rockgustavo.mscards.rest.dto.ClienteCartaoDTO;
import com.rockgustavo.mscards.service.ClienteCartaoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoServiceImpl implements ClienteCartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;
    private final ModelMapper modelMapper;

    public List<ClienteCartaoDTO> listCartoesByCpf(String cpf) {
        return clienteCartaoRepository.findByCpf(cpf)
                .stream()
                .map(clienteCartao -> modelMapper.map(clienteCartao, ClienteCartaoDTO.class))
                .collect(Collectors.toList());
    }
}
