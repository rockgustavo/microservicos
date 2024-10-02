package com.rockgustavo.msavaliadorcredito.rest.api;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rockgustavo.msavaliadorcredito.model.entity.DadosCliente;

@FeignClient(value = "msclients", path = "/api/cliente")
public interface ApiClient {

    @GetMapping
    String status();

    @GetMapping(params = "cpf")
    ResponseEntity<Optional<DadosCliente>> dadosCliente(@RequestParam("cpf") String cpf);

}
