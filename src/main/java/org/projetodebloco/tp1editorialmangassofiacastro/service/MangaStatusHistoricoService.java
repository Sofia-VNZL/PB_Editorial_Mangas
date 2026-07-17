package org.projetodebloco.tp1editorialmangassofiacastro.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatusHistorico;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.MangaStatusHistoricoRepository;
import org.springframework.stereotype.Service;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;


@Service
@RequiredArgsConstructor

public class MangaStatusHistoricoService {

    private final MangaStatusHistoricoRepository historicoRepository;

    public void registrar (Manga manga, MangaStatus statusAnterior) {
        MangaStatusHistorico historico = MangaStatusHistorico.builder()
                .mangaId(manga.getId())
                .tituloManga(manga.getTitulo())
                .autorId(manga.getAutor() != null ? manga.getAutor().getId() : null)
                .nomeAutor(manga.getAutor() != null ? manga.getAutor().getNome() : null)
                .statusAnterior(statusAnterior)
                .statusNovo(manga.getStatus())
                .build();
        historicoRepository.save(historico);
    }

    public List<MangaStatusHistorico> listarTodos(){
        return historicoRepository.findAllByOrderByAlteradoEmDesc();
    }

    public List<MangaStatusHistorico> listarPorManga(Long mangaId)  {
        return historicoRepository.findByMangaIdOrderByAlteradoEmDesc(mangaId);
    }

    public List<MangaStatusHistorico> listarPorAutor(Long autorId) {
        return historicoRepository.findByAutorIdOrderByAlteradoEmDesc(autorId);
    }
}
