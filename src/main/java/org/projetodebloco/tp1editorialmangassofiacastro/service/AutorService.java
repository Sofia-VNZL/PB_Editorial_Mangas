package org.projetodebloco.tp1editorialmangassofiacastro.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AutorService {
    private final AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com id: " + id));
    }

    public List<Autor> buscarPorNome(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Autor criar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Long id, Autor dadosNovos) {
        Autor autor = buscarPorId(id);
        autor.setNome(dadosNovos.getNome());
        autor.setBiografia(dadosNovos.getBiografia());
        return autorRepository.save(autor);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        autorRepository.deleteById(id);
    }
}
