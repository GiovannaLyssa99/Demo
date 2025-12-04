package com.giovanna.turismo.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FotoRepository extends MongoRepository<Foto, String> {

    List<Foto> findByPontoId(Long pontoId);

    long countByPontoId(Long pontoId);
}