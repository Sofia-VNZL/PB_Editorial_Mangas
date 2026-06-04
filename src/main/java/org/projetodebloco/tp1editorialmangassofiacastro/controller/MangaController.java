package org.projetodebloco.tp1editorialmangassofiacastro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Genero;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.projetodebloco.tp1editorialmangassofiacastro.service.MangaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mangas")
@RequiredArgsConstructor

public class MangaController {
    private final MangaService mangaService;

    @GetMapping
    public ResponseEntity<List<Manga>> listarTodos(
            @RequestParam(required = false) Genero genero,
            @RequestParam(required = false) MangaStatus status,
            @RequestParam(required = false) Long autorId) {
        return ResponseEntity.ok(mangaService.filtrar(genero, status, autorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manga> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mangaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Manga> criar(@Valid @RequestBody Manga manga) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mangaService.criar(manga));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manga> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Manga manga) {
        return ResponseEntity.ok(mangaService.atualizar(id, manga));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Manga> atualizarStatus(
            @PathVariable Long id,
            @RequestParam MangaStatus status) {
        return ResponseEntity.ok(mangaService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mangaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
