package org.projetodebloco.tp1editorialmangassofiacastro.service;

import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.client.AvaliacaoClient;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaComAvaliacao;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MangaComAvaliacaoService {

    private final MangaService mangaService;
    private final AvaliacaoClient avaliacaoClient;

    public MangaComAvaliacao buscarComAvaliacao(Long mangaId) {
        Manga manga = mangaService.buscarPorId(mangaId);

        Double media = 0.0;
        Integer total = 0;

        try {
            Map resultado = avaliacaoClient.buscarMedia(mangaId);
            if (resultado != null) {
                Object mediaObj = resultado.get("media");
                Object totalObj = resultado.get("totalAvaliacoes");
                if (mediaObj != null) media = Double.parseDouble(mediaObj.toString());
                if (totalObj != null) total = Integer.parseInt(totalObj.toString());
            }
        } catch (Exception e) {
        }

        return MangaComAvaliacao.builder()
                .id(manga.getId())
                .titulo(manga.getTitulo())
                .sinopse(manga.getSinopse())
                .genero(manga.getGenero())
                .status(manga.getStatus())
                .nomeAutor(manga.getAutor() != null ? manga.getAutor().getNome() : null)
                .mediaAvaliacoes(media)
                .totalAvaliacoes(total)
                .build();
    }
}
