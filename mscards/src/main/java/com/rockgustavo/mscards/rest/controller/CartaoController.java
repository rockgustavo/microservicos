package com.rockgustavo.mscards.rest.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rockgustavo.mscards.rest.dto.CartaoDTO;
import com.rockgustavo.mscards.rest.dto.ClienteCartaoDTO;
import com.rockgustavo.mscards.service.CartaoService;
import com.rockgustavo.mscards.service.ClienteCartaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cartao")
@RequiredArgsConstructor
@Slf4j
public class CartaoController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        log.info("Obtendo o status do microservice de cartoes");
        return "MicroServiços de cartoes";
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> createDirectory(@RequestBody CartaoDTO cartaoDTO) {
        var cartaoSaved = cartaoService.save(cartaoDTO.toModel());
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(cartaoSaved.getId())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "id")
    public ResponseEntity<Optional<CartaoDTO>> dadosCartao(@RequestParam("id") Long id) {
        var cartao = cartaoService.getById(id);
        if (cartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartao);
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<CartaoDTO>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<CartaoDTO> cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartaoDTO> cartoes = clienteCartaoService.listCartoesByCpf(cpf);
        return ResponseEntity.ok(cartoes);
    }
}
