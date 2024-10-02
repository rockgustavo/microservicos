package com.rockgustavo.msavaliadorcredito.model.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Cartao {
    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
