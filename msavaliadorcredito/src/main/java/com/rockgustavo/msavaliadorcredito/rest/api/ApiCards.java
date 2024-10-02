package com.rockgustavo.msavaliadorcredito.rest.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rockgustavo.msavaliadorcredito.model.entity.Cartao;
import com.rockgustavo.msavaliadorcredito.model.entity.CartaoCliente;

@FeignClient(value = "mscards", path = "/api/cartao")
public interface ApiCards {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda);

}
