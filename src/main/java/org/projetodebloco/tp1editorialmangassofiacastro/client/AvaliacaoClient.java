package org.projetodebloco.tp1editorialmangassofiacastro.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class AvaliacaoClient {

    private final RestTemplate restTemplate;
    private final String avaliacaoServiceUrl;

    public AvaliacaoClient(@Value("${avaliacao.service.url}") String avaliacaoServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.avaliacaoServiceUrl = avaliacaoServiceUrl;
    }

    public Map buscarMedia(Long mangaId) {
        try {
            return restTemplate.getForObject(
                    avaliacaoServiceUrl + "/api/avaliacoes/media?mangaId=" + mangaId,
                    Map.class
            );
        } catch (Exception e) {
            return Map.of("mangaId", mangaId, "media", 0.0, "totalAvaliacoes", 0);
        }
    }

    public Object buscarAvaliacoes(Long mangaId) {
        try {
            return restTemplate.getForObject(
                    avaliacaoServiceUrl + "/api/avaliacoes?mangaId=" + mangaId,
                    Object.class
            );
        } catch (Exception e) {
            return List.of();
        }
    }

    public void criarAvaliacao(Long mangaId, int nota) { //lembrar de usar no mock inicial!!!!!!!!!!!!
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = "{\"mangaId\":" + mangaId + ",\"nota\":" + nota + "}";
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        restTemplate.postForObject(
                avaliacaoServiceUrl + "/api/avaliacoes",
                request,
                Object.class
        );
    }
}