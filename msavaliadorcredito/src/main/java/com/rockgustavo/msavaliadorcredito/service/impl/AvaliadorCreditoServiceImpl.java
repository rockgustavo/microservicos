package com.rockgustavo.msavaliadorcredito.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rockgustavo.msavaliadorcredito.exception.DadosClienteNotFoundException;
import com.rockgustavo.msavaliadorcredito.exception.ErroComunicacaoMicroservicesException;
import com.rockgustavo.msavaliadorcredito.model.entity.Cartao;
import com.rockgustavo.msavaliadorcredito.model.entity.CartaoAprovado;
import com.rockgustavo.msavaliadorcredito.model.entity.CartaoCliente;
import com.rockgustavo.msavaliadorcredito.model.entity.DadosCliente;
import com.rockgustavo.msavaliadorcredito.model.entity.RetornoAvaliacaoCliente;
import com.rockgustavo.msavaliadorcredito.model.entity.SituacaoCliente;
import com.rockgustavo.msavaliadorcredito.rest.api.ApiCards;
import com.rockgustavo.msavaliadorcredito.rest.api.ApiClient;
import com.rockgustavo.msavaliadorcredito.service.AvaliadorCreditoService;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoServiceImpl implements AvaliadorCreditoService {

    private final ApiClient repositoryFeignClient;
    private final ApiCards repositoryFeignCards;

    public String obterStatusMsClients() {
        return repositoryFeignClient.status();
    }

    public Optional<SituacaoCliente> obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<Optional<DadosCliente>> dadosClienteResponse = repositoryFeignClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> listCartoesClientResponse = repositoryFeignCards
                    .getCartoesByCliente(cpf);

            SituacaoCliente situacaoCliente = SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody().get())
                    .cartoes(listCartoesClientResponse.getBody())
                    .build();
            return Optional.of(situacaoCliente);
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException("Dados de cliente não encontrado!");
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public Optional<RetornoAvaliacaoCliente> realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            // Busca os dados do cliente
            ResponseEntity<Optional<DadosCliente>> dadosClienteResponse = repositoryFeignClient.dadosCliente(cpf);

            // Verifica se os dados do cliente estão presentes
            if (!dadosClienteResponse.getBody().isPresent()) {
                throw new DadosClienteNotFoundException("Dados de cliente não encontrado!");
            }

            // Busca os cartões de acordo com a renda
            ResponseEntity<List<Cartao>> listCartoesClientResponse = repositoryFeignCards.getCartoesRendaAte(renda);
            List<Cartao> cartoes = listCartoesClientResponse.getBody();

            // Mapeia os cartões aprovados
            List<CartaoAprovado> cartoesAprovados = cartoes
                    .stream()
                    .map(cartao -> CartaoAprovado
                            .builder()
                            .cartao(cartao.getNome())
                            .bandeira(cartao.getBandeira())
                            .limiteAprovado(calculaLimiteAprovado(
                                    BigDecimal.valueOf(dadosClienteResponse.getBody().get().getIdade()),
                                    cartao.getLimiteBasico()))
                            .build())
                    .collect(Collectors.toList());

            // Retorna a avaliação do cliente com os cartões aprovados
            return Optional.of(RetornoAvaliacaoCliente
                    .builder()
                    .cartoes(cartoesAprovados)
                    .build());

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException("Dados de cliente não encontrado!");
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    private BigDecimal calculaLimiteAprovado(BigDecimal idadeCliente, BigDecimal limiteBasico) {
        var fator = idadeCliente.divide(BigDecimal.valueOf(10));
        return fator.multiply(limiteBasico);
    }

}
