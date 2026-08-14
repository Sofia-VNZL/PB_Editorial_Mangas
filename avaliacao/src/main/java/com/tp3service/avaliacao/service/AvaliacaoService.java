package com.tp3service.avaliacao.service;
import com.tp3service.avaliacao.model.Avaliacao;
import com.tp3service.avaliacao.repository.AvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> listarPorManga(Long mangaId) {
        return avaliacaoRepository.findByMangaId(mangaId);
    }

    public Avaliacao buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada com id: " + id));
    }

    public Double calcularMedia(Long mangaId) {
        return avaliacaoRepository.calcularMediaPorMangaId(mangaId)
                .orElse(0.0);
    }

    public Avaliacao criar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao) ;
    }

    public void deletar(Long id) {
        buscarPorId(id);
        avaliacaoRepository.deleteById(id);
    }
}
