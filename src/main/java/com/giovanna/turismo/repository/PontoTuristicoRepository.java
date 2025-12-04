package com.giovanna.turismo.repository;

import com.giovanna.turismo.entity.PontoTuristico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PontoTuristicoRepository extends JpaRepository<PontoTuristico, Long> {

    boolean existsByNomeAndCidade(String nome, String cidade);

    List<PontoTuristico> findByCidadeIgnoreCase(String cidade);

    List<PontoTuristico> findByEstadoIgnoreCase(String estado);

    List<PontoTuristico> findByCidadeAndEstadoIgnoreCase(String cidade, String estado);
}