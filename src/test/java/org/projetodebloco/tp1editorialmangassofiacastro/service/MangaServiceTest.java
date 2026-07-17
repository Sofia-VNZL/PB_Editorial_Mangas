package org.projetodebloco.tp1editorialmangassofiacastro.service;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.projetodebloco.tp1editorialmangassofiacastro.model.*;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.AutorRepository;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.MangaRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MangaServiceTest {

    @Mock
    private MangaRepository mangaRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private MangaStatusHistoricoService historicoService;

    @InjectMocks
    private MangaService mangaService;

    @Test
    @DisplayName("Deve lançar exceção quando manga não encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        when(mangaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mangaService.buscarPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Manga não encontrado com id: 99");
    }

    @Test
    @DisplayName("Deve atualizar status e registar no histórico")
    void deveAtualizarStatusERegistarHistorico() {
        Autor autor = Autor.builder().id(1L).nome("Oda").build();
        Manga manga = Manga.builder()
                .id(1L).titulo("One Piece")
                .genero(Genero.SHONEN)
                .status(MangaStatus.EM_PUBLICACAO)
                .autor(autor).build();

        when(mangaRepository.findById(1L)).thenReturn(Optional.of(manga));
        when(mangaRepository.save(any(Manga.class))).thenReturn(manga);

        mangaService.atualizarStatus(1L, MangaStatus.CONCLUIDO);

        assertThat(manga.getStatus()).isEqualTo(MangaStatus.CONCLUIDO);
        verify(historicoService, times(1)).registrar(manga, MangaStatus.EM_PUBLICACAO);
    }

    @Test
    @DisplayName("Não deve registar histórico se status não mudou")
    void naoDeveRegistarHistoricoSeStatusIgual() {
        Manga manga = Manga.builder()
                .id(1L).titulo("One Piece")
                .genero(Genero.SHONEN)
                .status(MangaStatus.EM_PUBLICACAO)
                .autor(Autor.builder().id(1L).nome("Oda").build())
                .build();

        when(mangaRepository.findById(1L)).thenReturn(Optional.of(manga));

        mangaService.atualizarStatus(1L, MangaStatus.EM_PUBLICACAO);

        verify(historicoService, never()).registrar(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar manga com autor inexistente")
    void deveLancarExcecaoAutorInexistente() {
        Manga manga = Manga.builder()
                .titulo("Teste")
                .genero(Genero.SHONEN)
                .status(MangaStatus.EM_PUBLICACAO)
                .autor(Autor.builder().id(99L).build())
                .build();

        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mangaService.criar(manga))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Autor não encontrado");
    }
}
