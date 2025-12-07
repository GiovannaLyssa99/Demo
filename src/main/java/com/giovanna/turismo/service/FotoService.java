package com.giovanna.turismo.service;

import com.giovanna.turismo.mongo.Foto;
import com.giovanna.turismo.mongo.FotoRepository;
import com.giovanna.turismo.repository.PontoTuristicoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class FotoService {

    private final FotoRepository fotoRepo;
    private final PontoTuristicoRepository pontoRepo;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public FotoService(FotoRepository fotoRepo, PontoTuristicoRepository pontoRepo) {
        this.fotoRepo = fotoRepo;
        this.pontoRepo = pontoRepo;
    }

    public Foto salvarFoto(Long pontoId, Long usuarioId, MultipartFile arquivo, String titulo) throws IOException {
        if (!pontoRepo.existsById(pontoId)) {
            throw new IllegalArgumentException("Ponto turístico não encontrado");
        }
        long qtd = fotoRepo.countByPontoId(pontoId);
        if (qtd >= 10) {
            throw new IllegalArgumentException("Limite de 10 fotos por ponto atingido");
        }

        Path dir = Paths.get(uploadDir);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String ext = "";
        String orig = arquivo.getOriginalFilename();
        if (orig != null && orig.contains(".")) {
            ext = orig.substring(orig.lastIndexOf('.'));
        }
        String filename = UUID.randomUUID().toString() + ext;
        Path target = dir.resolve(filename);
        Files.copy(arquivo.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        Foto foto = new Foto(pontoId, usuarioId, filename, titulo, target.toString());
        return fotoRepo.save(foto);
    }

    public List<Foto> listarPorPonto(Long pontoId) {
        return fotoRepo.findByPontoId(pontoId);
    }

    public Foto buscarPorId(String id) {
        return fotoRepo.findById(id).orElseThrow(() -> new RuntimeException("Foto não encontrada"));
    }

    public byte[] recuperarImagem(String filename) throws IOException {
        Path path = Paths.get(uploadDir).resolve(filename);
        return Files.readAllBytes(path);
    }
}