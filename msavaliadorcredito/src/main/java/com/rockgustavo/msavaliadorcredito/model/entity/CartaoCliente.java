package com.rockgustavo.msavaliadorcredito.model.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartaoCliente {

    private String cpf;

    private Cartao cartao;

    private BigDecimal limite;
}
