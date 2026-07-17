package org.projetodebloco.tp1editorialmangassofiacastro.controller;
import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatusHistorico;
import org.projetodebloco.tp1editorialmangassofiacastro.service.MangaStatusHistoricoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/historico")

public class HistoricoController {

    private final MangaStatusHistoricoService historicoService;

    @GetMapping
    public ResponseEntity<List<MangaStatusHistorico>> listar(
            @RequestParam(required = false) Long mangaId,
            @RequestParam(required = false) Long autorId) {

        if (mangaId != null) {
            return ResponseEntity.ok(historicoService.listarPorManga(mangaId));
        }
        if (autorId != null) {
            return ResponseEntity.ok(historicoService.listarPorAutor(autorId));
        }
        return ResponseEntity.ok(historicoService.listarTodos());
    }
}
