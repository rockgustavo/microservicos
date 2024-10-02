package com.rockgustavo.msavaliadorcredito.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rockgustavo.msavaliadorcredito.exception.DadosClienteNotFoundException;
import com.rockgustavo.msavaliadorcredito.exception.ErroComunicacaoMicroservicesException;
import com.rockgustavo.msavaliadorcredito.model.entity.DadosAvaliacao;
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

    @GetMapping(value = "status-msclients")
    public String statusMsClient() {
        log.info("Comunicacao entre microservicos - MsClient");
        return avaliadorCreditoService.obterStatusMsClients();
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity<?> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            var situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente.get());
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
        try {
            var retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente.get());
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
