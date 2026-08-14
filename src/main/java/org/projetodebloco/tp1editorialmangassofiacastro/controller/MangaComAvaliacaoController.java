package org.projetodebloco.tp1editorialmangassofiacastro.controller;

import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaComAvaliacao;
import org.projetodebloco.tp1editorialmangassofiacastro.service.MangaComAvaliacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mangas")
@RequiredArgsConstructor
public class MangaComAvaliacaoController {

    private final MangaComAvaliacaoService mangaComAvaliacaoService;

    @GetMapping("/{id}/detalhes")
    public ResponseEntity<MangaComAvaliacao> buscarComAvaliacao(@PathVariable Long id) {
        return ResponseEntity.ok(mangaComAvaliacaoService.buscarComAvaliacao(id));
    }
}
