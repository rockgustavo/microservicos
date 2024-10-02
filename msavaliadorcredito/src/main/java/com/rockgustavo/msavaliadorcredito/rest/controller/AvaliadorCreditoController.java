package com.rockgustavo.msavaliadorcredito.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rockgustavo.msavaliadorcredito.model.entity.SituacaoCliente;
import com.rockgustavo.msavaliadorcredito.service.AvaliadorCreditoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/avaliacao-credito")
@RequiredArgsConstructor
@Slf4j
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microservice de avaliacao de credito");
        return "MicroServiços de avaliação de crédito";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        var situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        if (situacaoCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avaliadorCreditoService.obterSituacaoCliente(cpf).get());
    }
}
