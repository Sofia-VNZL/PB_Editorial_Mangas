package com.tp3service.avaliacao.repository;
import com.tp3service.avaliacao.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByMangaId(Long mangaId);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.mangaId = :mangaId")
    Optional<Double> calcularMediaPorMangaId(Long mangaId);
}

