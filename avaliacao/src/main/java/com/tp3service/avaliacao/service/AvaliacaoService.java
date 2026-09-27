package com.tp3service.avaliacao.service;

import com.tp3service.avaliacao.event.AvaliacaoEvent;
import com.tp3service.avaliacao.model.Avaliacao;
import com.tp3service.avaliacao.repository.AvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${avaliacao.rabbitmq.exchange}")
    private String exchange;

    @Value("${avaliacao.rabbitmq.routing-key}")
    private String routingKey;

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
        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        AvaliacaoEvent evento = AvaliacaoEvent.builder()
                .avaliacaoId(salva.getId())
                .mangaId(salva.getMangaId())
                .nota(salva.getNota())
                .avaliadoEm(salva.getAvaliadoEm())
                .build();

        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
        System.out.println("Evento publicado: avaliacao.criada para mangaId=" + salva.getMangaId());

        return salva;
    }

    public void deletar(Long id) {
        buscarPorId(id);
        avaliacaoRepository.deleteById(id);
    }
}
