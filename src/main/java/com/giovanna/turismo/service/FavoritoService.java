package com.giovanna.turismo.service;

import com.giovanna.turismo.entity.PontoTuristico;
import com.giovanna.turismo.entity.Usuario;
import com.giovanna.turismo.repository.PontoTuristicoRepository;
import com.giovanna.turismo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class FavoritoService {

    private final UsuarioRepository usuarioRepo;
    private final PontoTuristicoRepository pontoRepo;

    public FavoritoService(UsuarioRepository usuarioRepo, PontoTuristicoRepository pontoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.pontoRepo = pontoRepo;
    }

    @Transactional
    public void alternarFavorito(String email, Long pontoId) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        PontoTuristico ponto = pontoRepo.findById(pontoId)
                .orElseThrow(() -> new EntityNotFoundException("Ponto não encontrado"));

        if (usuario.getFavoritos().contains(ponto)) {
            usuario.getFavoritos().remove(ponto);
        } else {
            usuario.getFavoritos().add(ponto);
        }
        usuarioRepo.save(usuario);
    }

    public Set<PontoTuristico> listarFavoritos(String email) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return usuario.getFavoritos();
    }
}