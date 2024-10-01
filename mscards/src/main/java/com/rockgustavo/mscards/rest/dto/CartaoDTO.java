package com.rockgustavo.mscards.rest.dto;

import java.math.BigDecimal;

import com.rockgustavo.mscards.model.entity.BandeiraCartao;
import com.rockgustavo.mscards.model.entity.Cartao;

import lombok.Data;

@Data
public class CartaoDTO {
    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao toModel() {
        return new Cartao(nome, BandeiraCartao.valueOf(bandeira.toUpperCase()), renda, limiteBasico);
    }

}
