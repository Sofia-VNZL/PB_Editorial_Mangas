package org.projetodebloco.tp1editorialmangassofiacastro.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@FeignClient(name = "avaliacao-service", url = "${avaliacao.service.url}")
public interface AvaliacaoClient {

    @GetMapping("/api/avaliacoes/media")
    Map<String, Object> buscarMedia(@RequestParam("mangaId") Long mangaId);

    @GetMapping("/api/avaliacoes")
    List<Map<String, Object>> buscarAvaliacoes(@RequestParam("mangaId") Long mangaId);

    @PostMapping("/api/avaliacoes")
    Map<String, Object> criarAvaliacao(@RequestBody Map<String, Object> body);
}