package com.giovanna.turismo.service;

import com.giovanna.turismo.entity.PontoTuristico;
import com.giovanna.turismo.repository.PontoTuristicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PontoTuristicoService {

    private final PontoTuristicoRepository repo;

    public PontoTuristicoService(PontoTuristicoRepository repo) {
        this.repo = repo;
    }

    public PontoTuristico salvar(PontoTuristico p) {
        validarBasico(p);
        if (repo.existsByNomeAndCidade(p.getNome(), p.getCidade())) {
            throw new IllegalArgumentException("Já existe ponto turístico com esse nome nesta cidade.");
        }
        return repo.save(p);
    }

    public List<PontoTuristico> listarTodos() {
        return repo.findAll();
    }

    public PontoTuristico buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ponto turístico não encontrado"));
    }

    public PontoTuristico atualizar(Long id, PontoTuristico novo) {
        PontoTuristico existente = buscarPorId(id);

        validarBasico(novo);

        if (!existente.getNome().equalsIgnoreCase(novo.getNome())
                || !existente.getCidade().equalsIgnoreCase(novo.getCidade())) {
            if (repo.existsByNomeAndCidade(novo.getNome(), novo.getCidade())) {
                throw new IllegalArgumentException("Já existe ponto turístico com esse nome nesta cidade.");
            }
        }

        existente.setNome(novo.getNome());
        existente.setDescricao(novo.getDescricao());
        existente.setEndereco(novo.getEndereco());
        existente.setCidade(novo.getCidade());
        existente.setEstado(novo.getEstado());
        existente.setPais(novo.getPais());
        existente.setLatitude(novo.getLatitude());
        existente.setLongitude(novo.getLongitude());

        return repo.save(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Ponto turístico não encontrado");
        }
        repo.deleteById(id);
    }

    private void validarBasico(PontoTuristico p) {
        if (p == null) throw new IllegalArgumentException("PontoTuristico é obrigatório");
        if (p.getNome() == null || p.getNome().isBlank())
            throw new IllegalArgumentException("Nome do ponto é obrigatório");
        if (p.getCidade() == null || p.getCidade().isBlank())
            throw new IllegalArgumentException("Cidade é obrigatória");
        if (p.getDescricao() == null || p.getDescricao().isBlank())
            throw new IllegalArgumentException("Descrição é obrigatória");
    }
}