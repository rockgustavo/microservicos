package com.rockgustavo.msavaliadorcredito.service;

import java.util.Optional;

import com.rockgustavo.msavaliadorcredito.model.entity.SituacaoCliente;

public interface AvaliadorCreditoService {

    Optional<SituacaoCliente> obterSituacaoCliente(String cpf);
}
