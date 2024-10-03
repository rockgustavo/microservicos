package com.rockgustavo.msavaliadorcredito.rest.mqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rockgustavo.msavaliadorcredito.model.entity.DadosSolicitacaoEmissaoCartao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SolicitacaoEmissaoCartaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        log.info("Solicitar Cartão: {}", dados);
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), dados);
    }
}
