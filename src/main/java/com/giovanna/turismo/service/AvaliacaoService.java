package com.giovanna.turismo.service;

import com.giovanna.turismo.entity.Avaliacao;
import com.giovanna.turismo.entity.PontoTuristico;
import com.giovanna.turismo.repository.AvaliacaoRepository;
import com.giovanna.turismo.repository.PontoTuristicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.giovanna.turismo.entity.Usuario;
import com.giovanna.turismo.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepo;
    private final PontoTuristicoRepository pontoRepo;
private final UsuarioRepository usuarioRepo;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepo, PontoTuristicoRepository pontoRepo, UsuarioRepository usuarioRepo) {
        this.avaliacaoRepo = avaliacaoRepo;
        this.pontoRepo = pontoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Transactional
    public Avaliacao criarOuAtualizar(Avaliacao req) {
        if (req.getNota() == null)
            throw new IllegalArgumentException("Nota obrigatória");
        if (req.getNota() < 1 || req.getNota() > 5)
            throw new IllegalArgumentException("Nota deve ser inteira entre 1 e 5");
        if (req.getPontoId() == null)
            throw new IllegalArgumentException("pontoId obrigatório");
        if (req.getUsuarioId() == null)
            throw new IllegalArgumentException("usuarioId obrigatório");

        PontoTuristico ponto = pontoRepo.findById(req.getPontoId())
                .orElseThrow(() -> new EntityNotFoundException("Ponto turístico não encontrado"));

        return avaliacaoRepo.findByPontoIdAndUsuarioId(req.getPontoId(), req.getUsuarioId())
                .map(existing -> {
                    existing.setNota(req.getNota());
                    existing.setComentario(req.getComentario());
                    existing.setCreatedAt(LocalDateTime.now());
                    Avaliacao saved = avaliacaoRepo.save(existing);
                    recalcularMedia(ponto.getId());
                    return saved;
                })
                .orElseGet(() -> {
                    Avaliacao nova = new Avaliacao();
                    nova.setPontoId(req.getPontoId());
                    nova.setUsuarioId(req.getUsuarioId());
                    nova.setNota(req.getNota());
                    nova.setComentario(req.getComentario());
                    nova.setCreatedAt(LocalDateTime.now());
                    Avaliacao saved = avaliacaoRepo.save(nova);
                    recalcularMedia(ponto.getId());
                    return saved;
                });
    }

    public List<Avaliacao> listarPorPonto(Long pontoId) {
        return avaliacaoRepo.findByPontoIdOrderByCreatedAtDesc(pontoId);
    }

    private void recalcularMedia(Long pontoId) {
        Double media = avaliacaoRepo.avgNotaByPontoId(pontoId);
        if (media == null) media = 0.0;

        PontoTuristico p = pontoRepo.findById(pontoId)
                .orElseThrow(() -> new EntityNotFoundException("Ponto turístico não encontrado"));

        p.setMediaAvaliacoes(media);
        pontoRepo.save(p);
    }

public void deletarPorId(Long id, String emailUsuarioLogado, boolean isAdmin) {
        Avaliacao av = avaliacaoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada"));

        if (!isAdmin) {
            Usuario usuario = usuarioRepo.findByEmail(emailUsuarioLogado)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário logado não encontrado no banco"));
            
            if (!av.getUsuarioId().equals(usuario.getId())) {
                throw new AccessDeniedException("Você não tem permissão para excluir esta avaliação.");
            }
        }

        Long pontoId = av.getPontoId();
        avaliacaoRepo.deleteById(id);
        recalcularMedia(pontoId);
    }
}