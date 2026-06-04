package org.projetodebloco.tp1editorialmangassofiacastro.repository;

import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor; //provavelmente tenha que mudar o nome para melhor leitura do código
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}