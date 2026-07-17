package org.projetodebloco.tp1editorialmangassofiacastro.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Genero;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.AutorRepository;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.MangaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class MangaService {
    private final MangaRepository mangaRepository;
    private final AutorRepository autorRepository;
    private final MangaStatusHistoricoService historicoService;

    public List<Manga> listarTodos() {

        return mangaRepository.findAll();
    }

    public Manga buscarPorId(Long id) {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manga não encontrado com id: " + id));
    }

    public List<Manga> filtrar(Genero genero, MangaStatus status, Long autorId) {
        if (genero != null) return mangaRepository.findByGenero(genero);
        if (status != null) return mangaRepository.findByStatus(status);
        if (autorId != null) return mangaRepository.findByAutorId(autorId);
        return mangaRepository.findAll();
    }
    public Manga criar(Manga manga) {
        if (manga.getAutor() != null && manga.getAutor().getId() != null) {
            autorRepository.findById(manga.getAutor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        }
        return mangaRepository.save(manga);
    }

    public Manga atualizar(Long id, Manga dadosNovos) {
        Manga manga = buscarPorId(id);
        manga.setTitulo(dadosNovos.getTitulo());
        manga.setSinopse(dadosNovos.getSinopse());
        manga.setGenero(dadosNovos.getGenero());
        if (dadosNovos.getStatus() != null && !dadosNovos.getStatus().equals(manga.getStatus())) {
            MangaStatus statusAnterior = manga.getStatus();
            manga.setStatus(dadosNovos.getStatus());
            mangaRepository.save(manga);
            historicoService.registrar(manga, statusAnterior);
            return manga;
        }

        manga.setStatus(dadosNovos.getStatus());

        if (dadosNovos.getAutor() != null && dadosNovos.getAutor().getId() != null) {
            manga.setAutor(autorRepository.findById(dadosNovos.getAutor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado")));
        }
        return mangaRepository.save(manga);
    }

    public Manga atualizarStatus(Long id, MangaStatus novoStatus) {
        Manga manga = buscarPorId(id);
        MangaStatus statusAnterior = manga.getStatus();

        if (!statusAnterior.equals(novoStatus)) {
            manga.setStatus(novoStatus);
            mangaRepository.save(manga);
            historicoService.registrar(manga, statusAnterior);
        }

        return manga;
    }

    public void deletar(Long id) {
        buscarPorId(id);
        mangaRepository.deleteById(id);
    }
}
