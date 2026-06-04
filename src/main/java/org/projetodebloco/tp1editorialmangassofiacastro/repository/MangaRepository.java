package org.projetodebloco.tp1editorialmangassofiacastro.repository;


import org.projetodebloco.tp1editorialmangassofiacastro.model.Genero;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {
    List<Manga> findByGenero(Genero genero);
    List<Manga> findByStatus(MangaStatus status);
    List<Manga> findByAutorId(Long autorId);
    List<Manga> findByTituloContainingIgnoreCase(String titulo);
}
