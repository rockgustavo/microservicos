package com.rockgustavo.msclients.service;

import java.util.Optional;

import com.rockgustavo.msclients.model.entity.Cliente;
import com.rockgustavo.msclients.rest.dto.ClienteDTO;

public interface ClienteService {
    ClienteDTO save(Cliente cliente);

    Optional<ClienteDTO> getByCpf(String cpf);

}
