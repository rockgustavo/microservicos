package com.rockgustavo.mscards.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rockgustavo.mscards.model.entity.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

    List<ClienteCartao> findByCpf(String cpf);
}