package com.tp3service.avaliacao.controller;
import com.tp3service.avaliacao.model.Avaliacao;
import com.tp3service.avaliacao.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor

public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<List<Avaliacao>> listar(
            @RequestParam(required = false) Long mangaId) {
        if (mangaId != null) {
            return ResponseEntity.ok(avaliacaoService.listarPorManga(mangaId));
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avaliacaoService.buscarPorId(id));
    }
    @GetMapping("/media")
    public ResponseEntity<Map<String, Object>> media(
            @RequestParam Long mangaId) {
        Double media = avaliacaoService.calcularMedia(mangaId);
        return ResponseEntity.ok(Map.of(
                "mangaId", mangaId,
                "media", media,
                "totalAvaliacoes", avaliacaoService.listarPorManga(mangaId).size()
        ));
    }

    @PostMapping
    public ResponseEntity<Avaliacao> criar(@Valid @RequestBody Avaliacao avaliacao) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(avaliacaoService.criar(avaliacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
