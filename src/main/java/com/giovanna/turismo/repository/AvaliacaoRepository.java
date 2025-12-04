package com.giovanna.turismo.repository;

import com.giovanna.turismo.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByPontoIdOrderByCreatedAtDesc(Long pontoId);

    Optional<Avaliacao> findByPontoIdAndUsuarioId(Long pontoId, Long usuarioId);

    long countByPontoId(Long pontoId);

    @Query("select avg(a.nota) from Avaliacao a where a.pontoId = :pontoId")
    Double avgNotaByPontoId(Long pontoId);
}