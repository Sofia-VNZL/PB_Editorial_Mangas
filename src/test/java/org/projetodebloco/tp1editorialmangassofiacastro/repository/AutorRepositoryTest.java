package org.projetodebloco.tp1editorialmangassofiacastro.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")

public class AutorRepositoryTest {
    @Autowired
    private AutorRepository autorRepository;

    @Test
    @DisplayName("Deve salvar e recuperar um autor por ID")
    void deveSalvarERecuperarAutor() {
        Autor autor = Autor.builder()
                .nome("Eiichiro Oda")
                .biografia("é um mangaká, sendo mais conhecido como criador da série One Piece. One Piece é o mangá mais vendido de todos os tempos com 500 milhões de cópias vendidas em todo o mundo, rendendo a Oda o título de um dos autores de ficção mais vendidos. ")
                .build();

        Autor salvo = autorRepository.save(autor);

        Optional<Autor> encontrado = autorRepository.findById(salvo.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Eiichiro Oda");
    }

    @Test
    @DisplayName("Deve buscar autores pelo nome ignorando maiúsculas")
    void deveBuscarPorNomeIgnoreCase() {
        Autor autor = Autor.builder()
                .nome("Masashi Kishimoto")
                .biografia("Criador de Naruto")
                .build();
        autorRepository.save(autor);

        List<Autor> resultado = autorRepository.findByNomeContainingIgnoreCase("kishimoto");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Masashi Kishimoto");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nome não existe")
    void deveRetornarVazioQuandoNomeNaoExiste() {
        List<Autor> resultado = autorRepository.findByNomeContainingIgnoreCase("Tolkien");
        assertThat(resultado).isEmpty();
    }
    @Test
    @DisplayName("Deve deletar um autor")
    void deveDeletarAutor() {
        Autor autor = Autor.builder()
                .nome("Hajime Isayama")
                .biografia("Criador de Attack on Titan")
                .build();
        Autor salvo = autorRepository.save(autor);

        autorRepository.deleteById(salvo.getId());

        assertThat(autorRepository.findById(salvo.getId())).isEmpty();
    }
}