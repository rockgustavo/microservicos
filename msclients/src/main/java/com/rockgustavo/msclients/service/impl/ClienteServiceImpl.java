package com.rockgustavo.msclients.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rockgustavo.msclients.model.entity.Cliente;
import com.rockgustavo.msclients.model.repository.ClienteRepository;
import com.rockgustavo.msclients.rest.dto.ClienteDTO;
import com.rockgustavo.msclients.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ClienteDTO save(Cliente cliente) {

        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    public Optional<ClienteDTO> getByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class));
    }
}
