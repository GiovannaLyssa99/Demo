package com.giovanna.turismo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {

    List<Comentario> findByPontoIdOrderByCreatedAtDesc(Long pontoId);
}