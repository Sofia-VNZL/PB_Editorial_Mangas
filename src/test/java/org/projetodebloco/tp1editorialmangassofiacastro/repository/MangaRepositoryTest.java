package org.projetodebloco.tp1editorialmangassofiacastro.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Genero;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;



@DataJpaTest
@ActiveProfiles("test")
public class MangaRepositoryTest {

    @Autowired
    private MangaRepository mangaRepository;

    @Autowired
    private AutorRepository autorRepository;

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = autorRepository.save(Autor.builder()
                .nome("Eiichiro Oda")
                .biografia("é um mangaká, sendo mais conhecido como criador da série One Piece. One Piece é o mangá mais vendido de todos os tempos com 500 milhões de cópias vendidas em todo o mundo, rendendo a Oda o título de um dos autores de ficção mais vendidos.")
                .build());
    }

    @Test
    @DisplayName("Deve salvar e recuperar um manga")
    void deveSalvarERecuperarManga() {
        Manga manga = Manga.builder()
                .titulo("One Piece")
                .sinopse("A jornada de Luffy")
                .genero(Genero.SHONEN)
                .status(MangaStatus.EM_PUBLICACAO)
                .autor(autor)
                .build();

        Manga salvo = mangaRepository.save(manga);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getTitulo()).isEqualTo("One Piece");
        assertThat(salvo.getAutor().getNome()).isEqualTo("Eiichiro Oda");
    }

    @Test
    @DisplayName("Deve filtrar mangas por gênero")
    void deveFiltrarPorGenero() {
        mangaRepository.save(Manga.builder()
                .titulo("One Piece").sinopse("...")
                .genero(Genero.SHONEN).status(MangaStatus.EM_PUBLICACAO)
                .autor(autor).build());

        mangaRepository.save(Manga.builder()
                .titulo("Berserk").sinopse("...")
                .genero(Genero.SEINEN).status(MangaStatus.PAUSADO)
                .autor(autor).build());

        List<Manga> shonen = mangaRepository.findByGenero(Genero.SHONEN);
        assertThat(shonen).hasSize(1);
        assertThat(shonen.get(0).getTitulo()).isEqualTo("One Piece");
    }
    @Test
    @DisplayName("Deve filtrar mangas por status")
    void deveFiltrarPorStatus() {
        mangaRepository.save(Manga.builder()
                .titulo("One Piece").sinopse("...")
                .genero(Genero.SHONEN).status(MangaStatus.EM_PUBLICACAO)
                .autor(autor).build());

        mangaRepository.save(Manga.builder()
                .titulo("Naruto").sinopse("...")
                .genero(Genero.SHONEN).status(MangaStatus.CONCLUIDO)
                .autor(autor).build());

        List<Manga> concluidos = mangaRepository.findByStatus(MangaStatus.CONCLUIDO);
        assertThat(concluidos).hasSize(1);
        assertThat(concluidos.get(0).getTitulo()).isEqualTo("Naruto");
    }
    @Test
    @DisplayName("Deve filtrar mangas por autor")
    void deveFiltrarPorAutor() {
        Autor outroAutor = autorRepository.save(Autor.builder()
                .nome("Masashi Kishimoto")
                .biografia("Criador de Naruto")
                .build());

        mangaRepository.save(Manga.builder()
                .titulo("One Piece").sinopse("...")
                .genero(Genero.SHONEN).status(MangaStatus.EM_PUBLICACAO)
                .autor(autor).build());

        mangaRepository.save(Manga.builder()
                .titulo("Naruto").sinopse("...")
                .genero(Genero.SHONEN).status(MangaStatus.CONCLUIDO)
                .autor(outroAutor).build());

        List<Manga> obrasOda = mangaRepository.findByAutorId(autor.getId());
        assertThat(obrasOda).hasSize(1);
        assertThat(obrasOda.get(0).getTitulo()).isEqualTo("One Piece");
    }
}
