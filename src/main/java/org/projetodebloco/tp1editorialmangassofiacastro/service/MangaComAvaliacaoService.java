package org.projetodebloco.tp1editorialmangassofiacastro.service;

import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.client.AvaliacaoClient;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaComAvaliacao;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaMediaCache;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.MangaMediaCacheRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MangaComAvaliacaoService {

    private final MangaService mangaService;
    //private final AvaliacaoClient avaliacaoClient;
    private final MangaMediaCacheRepository cacheRepository;


    public MangaComAvaliacao buscarComAvaliacao(Long mangaId) {
        Manga manga = mangaService.buscarPorId(mangaId);

        MangaMediaCache cache = cacheRepository.findById(mangaId)
                .orElse(MangaMediaCache.builder()
                        .mangaId(mangaId)
                        .mediaAvaliacoes(0.0)
                        .totalAvaliacoes(0)
                        .build());

        return MangaComAvaliacao.builder()
                .id(manga.getId())
                .titulo(manga.getTitulo())
                .sinopse(manga.getSinopse())
                .genero(manga.getGenero())
                .status(manga.getStatus())
                .nomeAutor(manga.getAutor() != null ? manga.getAutor().getNome() : null)
                .mediaAvaliacoes(cache.getMediaAvaliacoes())
                .totalAvaliacoes(cache.getTotalAvaliacoes())
                .build();
    }
}
