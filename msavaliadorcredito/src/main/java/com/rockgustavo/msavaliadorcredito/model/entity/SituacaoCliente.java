package com.rockgustavo.msavaliadorcredito.model.entity;

import java.util.List;

import lombok.Data;

@Data
public class SituacaoCliente {

    private DadosCliente cliente;
    private List<CartaoCliente> cartoes;
}
