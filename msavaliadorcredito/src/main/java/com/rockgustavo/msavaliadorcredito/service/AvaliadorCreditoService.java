package com.rockgustavo.msavaliadorcredito.service;

import java.util.Optional;

import com.rockgustavo.msavaliadorcredito.exception.DadosClienteNotFoundException;
import com.rockgustavo.msavaliadorcredito.exception.ErroComunicacaoMicroservicesException;
import com.rockgustavo.msavaliadorcredito.model.entity.DadosSolicitacaoEmissaoCartao;
import com.rockgustavo.msavaliadorcredito.model.entity.ProtocoloSolicitacaoCartao;
import com.rockgustavo.msavaliadorcredito.model.entity.RetornoAvaliacaoCliente;
import com.rockgustavo.msavaliadorcredito.model.entity.SituacaoCliente;

public interface AvaliadorCreditoService {

        String obterStatusMsClients();

        Optional<SituacaoCliente> obterSituacaoCliente(String cpf)
                        throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException;

        Optional<RetornoAvaliacaoCliente> realizarAvaliacao(String cpf, Long renda)
                        throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException;

        ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados);

}
