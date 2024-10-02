package com.rockgustavo.msavaliadorcredito.model.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;
}
