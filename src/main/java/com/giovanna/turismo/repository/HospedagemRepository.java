package com.giovanna.turismo.repository;

import com.giovanna.turismo.entity.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {

    List<Hospedagem> findByPontoId(Long pontoId);

    boolean existsByNomeAndPontoId(String nome, Long pontoId);
}