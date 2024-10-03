package com.rockgustavo.mscards.rest.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockgustavo.mscards.model.entity.Cartao;
import com.rockgustavo.mscards.model.entity.ClienteCartao;
import com.rockgustavo.mscards.model.entity.DadosSolicitacaoEmissaoCartao;
import com.rockgustavo.mscards.model.repository.CartaoRepository;
import com.rockgustavo.mscards.model.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        log.info("RabbitMq - Payload: " + payload);

        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dadosQueue = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartaoQueue = cartaoRepository.findById(dadosQueue.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartaoQueue);
            clienteCartao.setCpf(dadosQueue.getCpf());
            clienteCartao.setLimite(dadosQueue.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
