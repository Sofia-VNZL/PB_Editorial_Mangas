package com.tp3service.avaliacao.repository;


import com.tp3service.avaliacao.model.Avaliacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")

public class AvaliacaoRepositoryTest {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Test
    @DisplayName("Deve salvar e recuperar uma avaliação")
    void deveSalvarERecuperarAvaliacao() {
        Avaliacao avaliacao = Avaliacao.builder()
                .mangaId(1L)
                .nota(5)
                .build();

        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        Optional<Avaliacao> encontrada = avaliacaoRepository.findById(salva.getId());
        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNota()).isEqualTo(5);
        assertThat(encontrada.get().getAvaliadoEm()).isNotNull();
    }

    @Test
    @DisplayName("Deve listar avaliações por mangaId")
    void deveListarPorMangaId() {
        avaliacaoRepository.save(Avaliacao.builder().mangaId(1L).nota(5).build());
        avaliacaoRepository.save(Avaliacao.builder().mangaId(1L).nota(4).build());
        avaliacaoRepository.save(Avaliacao.builder().mangaId(2L).nota(3).build());

        List<Avaliacao> resultado = avaliacaoRepository.findByMangaId(1L);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(a -> a.getMangaId().equals(1L));
    }

    @Test
    @DisplayName("Deve calcular média de avaliações por mangaId")
    void deveCalcularMedia() {
        avaliacaoRepository.save(Avaliacao.builder().mangaId(1L).nota(4).build());
        avaliacaoRepository.save(Avaliacao.builder().mangaId(1L).nota(5).build());
        avaliacaoRepository.save(Avaliacao.builder().mangaId(1L).nota(3).build());

        Optional<Double> media = avaliacaoRepository.calcularMediaPorMangaId(1L);

        assertThat(media).isPresent();
        assertThat(media.get()).isEqualTo(4.0);
    }

    @Test
    @DisplayName("Deve retornar vazio quando não há avaliações para o manga")
    void deveRetornarVazioSemAvaliacoes() {
        List<Avaliacao> resultado = avaliacaoRepository.findByMangaId(99L);
        assertThat(resultado).isEmpty();
    }
}
