package com.rockgustavo.mscards.rest.dto;

import java.math.BigDecimal;

import com.rockgustavo.mscards.model.entity.Cartao;
import com.rockgustavo.mscards.model.entity.ClienteCartao;

import lombok.Data;

@Data
public class ClienteCartaoDTO {

    private String cpf;

    private Cartao cartao;

    private BigDecimal limite;

    public ClienteCartao toModel() {
        return new ClienteCartao(cpf, cartao, limite);
    }
}
