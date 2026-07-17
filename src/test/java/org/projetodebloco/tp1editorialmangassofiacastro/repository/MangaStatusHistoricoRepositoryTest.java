package org.projetodebloco.tp1editorialmangassofiacastro.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatusHistorico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class MangaStatusHistoricoRepositoryTest {

    @Autowired
    private MangaStatusHistoricoRepository historicoRepository;

    @Test
    @DisplayName("Deve salvar e recuperar histórico")
    void deveSalvarHistorico() {
        MangaStatusHistorico historico = MangaStatusHistorico.builder()
                .mangaId(1L)
                .tituloManga("One Piece")
                .autorId(1L)
                .nomeAutor("Eiichiro Oda")
                .statusAnterior(MangaStatus.EM_PUBLICACAO)
                .statusNovo(MangaStatus.CONCLUIDO)
                .build();

        MangaStatusHistorico salvo = historicoRepository.save(historico);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getAlteradoEm()).isNotNull();
        assertThat(salvo.getStatusNovo()).isEqualTo(MangaStatus.CONCLUIDO);
    }
    @Test
    @DisplayName("Deve listar histórico por mangaId ordenado do mais recente")
    void deveListarPorMangaId() {
        historicoRepository.save(MangaStatusHistorico.builder()
                .mangaId(1L).tituloManga("One Piece")
                .autorId(1L).nomeAutor("Oda")
                .statusAnterior(MangaStatus.EM_PUBLICACAO)
                .statusNovo(MangaStatus.PAUSADO).build());

        historicoRepository.save(MangaStatusHistorico.builder()
                .mangaId(1L).tituloManga("One Piece")
                .autorId(1L).nomeAutor("Oda")
                .statusAnterior(MangaStatus.PAUSADO)
                .statusNovo(MangaStatus.CONCLUIDO).build());

        List<MangaStatusHistorico> resultado =
                historicoRepository.findByMangaIdOrderByAlteradoEmDesc(1L);

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getStatusNovo()).isEqualTo(MangaStatus.CONCLUIDO);
    }
    @Test
    @DisplayName("Deve listar histórico por autorId")
    void deveListarPorAutorId() {
        historicoRepository.save(MangaStatusHistorico.builder()
                .mangaId(1L).tituloManga("One Piece")
                .autorId(1L).nomeAutor("Oda")
                .statusAnterior(MangaStatus.EM_PUBLICACAO)
                .statusNovo(MangaStatus.CONCLUIDO).build());

        historicoRepository.save(MangaStatusHistorico.builder()
                .mangaId(2L).tituloManga("Naruto")
                .autorId(2L).nomeAutor("Kishimoto")
                .statusAnterior(MangaStatus.EM_PUBLICACAO)
                .statusNovo(MangaStatus.CONCLUIDO).build());

        List<MangaStatusHistorico> resultado =
                historicoRepository.findByAutorIdOrderByAlteradoEmDesc(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTituloManga()).isEqualTo("One Piece");
    }
}
