package com.tp3service.avaliacao.service;

import com.tp3service.avaliacao.model.Avaliacao;
import com.tp3service.avaliacao.repository.AvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Test
    @DisplayName("Deve listar avaliações por manga")
    void deveListarPorManga() {
        List<Avaliacao> avaliacoes = List.of(
                Avaliacao.builder().id(1L).mangaId(1L).nota(5).build(),
                Avaliacao.builder().id(2L).mangaId(1L).nota(4).build()
        );
        when(avaliacaoRepository.findByMangaId(1L)).thenReturn(avaliacoes);

        List<Avaliacao> resultado = avaliacaoService.listarPorManga(1L);

        assertThat(resultado).hasSize(2);
        verify(avaliacaoRepository, times(1)).findByMangaId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando avaliação não encontrada")
    void deveLancarExcecaoQuandoNaoEncontrada() {
        when(avaliacaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> avaliacaoService.buscarPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Avaliação não encontrada com id: 99");
    }

    @Test
    @DisplayName("Deve calcular média corretamente")
    void deveCalcularMedia() {
        when(avaliacaoRepository.calcularMediaPorMangaId(1L))
                .thenReturn(Optional.of(4.5));

        Double media = avaliacaoService.calcularMedia(1L);

        assertThat(media).isEqualTo(4.5);
    }

    @Test
    @DisplayName("Deve retornar zero quando não há avaliações")
    void deveRetornarZeroSemAvaliacoes() {
        when(avaliacaoRepository.calcularMediaPorMangaId(99L))
                .thenReturn(Optional.empty());

        Double media = avaliacaoService.calcularMedia(99L);

        assertThat(media).isEqualTo(0.0);
    }

    @Test
    @DisplayName("deve criar avaliação e publicar evento")
    void deveCriarAvaliacao() {
        ReflectionTestUtils.setField(avaliacaoService, "exchange", "avaliacao.exchange");
        ReflectionTestUtils.setField(avaliacaoService, "routingKey", "avaliacao.criada");

        Avaliacao avaliacao = Avaliacao.builder().mangaId(1L).nota(5).build();
        Avaliacao salva = Avaliacao.builder().id(1L).mangaId(1L).nota(5).build();
        when(avaliacaoRepository.save(avaliacao)).thenReturn(salva);
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(Object.class));

        Avaliacao resultado = avaliacaoService.criar(avaliacao);

        assertThat(resultado.getId()).isEqualTo(1L);
        verify(avaliacaoRepository, times(1)).save(avaliacao);
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(Object.class));
    }

    @Test
    @DisplayName("deve deletar avaliação existente ")
    void deveDeletarAvaliacao() {
        Avaliacao avaliacao = Avaliacao.builder().id(1L).mangaId(1L).nota(5).build();
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        avaliacaoService.deletar(1L);

        verify(avaliacaoRepository, times(1)).deleteById(1L);
    }
}