package org.projetodebloco.tp1editorialmangassofiacastro.controller;

import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor;
import org.projetodebloco.tp1editorialmangassofiacastro.service.AutorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor

public class AutorController {
    private final AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos(
            @RequestParam(required = false) String nome) {
        if (nome != null) {
            return ResponseEntity.ok(autorService.buscarPorNome(nome));
        }
        return ResponseEntity.ok(autorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Autor> criar(@Valid @RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.criar(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Autor autor) {
        return ResponseEntity.ok(autorService.atualizar(id, autor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
