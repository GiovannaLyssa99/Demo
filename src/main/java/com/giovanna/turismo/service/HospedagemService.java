package com.giovanna.turismo.service;

import com.giovanna.turismo.entity.Hospedagem;
import com.giovanna.turismo.entity.PontoTuristico;
import com.giovanna.turismo.repository.HospedagemRepository;
import com.giovanna.turismo.repository.PontoTuristicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospedagemService {

    private final HospedagemRepository repo;
    private final PontoTuristicoRepository pontoRepo;

    public HospedagemService(HospedagemRepository repo, PontoTuristicoRepository pontoRepo) {
        this.repo = repo;
        this.pontoRepo = pontoRepo;
    }

    public Hospedagem criar(Hospedagem h) {
        validar(h);

        pontoRepo.findById(h.getPontoId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Ponto turístico não encontrado"));

        if (repo.existsByNomeAndPontoId(h.getNome(), h.getPontoId())) {
            throw new IllegalArgumentException("Já existe hospedagem com este nome neste ponto turístico.");
        }

        return repo.save(h);
    }

    public List<Hospedagem> listarPorPonto(Long pontoId) {
        return repo.findByPontoId(pontoId);
    }

    public Hospedagem buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Hospedagem não encontrada"));
    }

    public Hospedagem atualizar(Long id, Hospedagem nova) {
        validar(nova);

        Hospedagem existente = buscarPorId(id);

        existente.setNome(nova.getNome());
        existente.setEndereco(nova.getEndereco());
        existente.setTelefone(nova.getTelefone());
        existente.setPrecoMedio(nova.getPrecoMedio());
        existente.setTipo(nova.getTipo());
        existente.setLinkReserva(nova.getLinkReserva());
        existente.setPontoId(nova.getPontoId());

        return repo.save(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Hospedagem não encontrada");
        }
        repo.deleteById(id);
    }

    private void validar(Hospedagem h) {
        if (h.getNome() == null || h.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da hospedagem é obrigatório.");
        }
        if (h.getPontoId() == null) {
            throw new IllegalArgumentException("pontoId é obrigatório.");
        }
    }
}