package org.projetodebloco.tp1editorialmangassofiacastro.listener;

import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.event.AvaliacaoEvent;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaMediaCache;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.MangaMediaCacheRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvaliacaoEventListener {
    private final MangaMediaCacheRepository cacheRepository;

    @RabbitListener(queues = "${avaliacao.rabbitmq.queue}")
    public void onAvaliacaoCriada(AvaliacaoEvent evento) {
        System.out.println("Evento recebido: avaliacao.criada para mangaId=" + evento.getMangaId());

        MangaMediaCache cache = cacheRepository.findById(evento.getMangaId())
                .orElse(MangaMediaCache.builder()
                        .mangaId(evento.getMangaId())
                        .mediaAvaliacoes(0.0)
                        .totalAvaliacoes(0)
                        .build());

        int novoTotal = cache.getTotalAvaliacoes() + 1;
        double novaMedia = ((cache.getMediaAvaliacoes() * cache.getTotalAvaliacoes())
                + evento.getNota()) / novoTotal;

        cache.setTotalAvaliacoes(novoTotal);
        cache.setMediaAvaliacoes(Math.round(novaMedia * 10.0) / 10.0);

        cacheRepository.save(cache);
        System.out.println("Cache atualizado: mangaId=" + evento.getMangaId()
                + " média=" + cache.getMediaAvaliacoes()
                + " total=" + novoTotal);
    }
}
