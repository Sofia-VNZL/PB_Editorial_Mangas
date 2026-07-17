package org.projetodebloco.tp1editorialmangassofiacastro.repository;
import org. projetodebloco.tp1editorialmangassofiacastro.model.MangaStatusHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface MangaStatusHistoricoRepository extends JpaRepository<MangaStatusHistorico, Long> {
    List<MangaStatusHistorico> findByMangaIdOrderByAlteradoEmDesc(Long mangaId);

    List<MangaStatusHistorico> findByAutorIdOrderByAlteradoEmDesc(Long autorId);

    List<MangaStatusHistorico> findAllByOrderByAlteradoEmDesc();
}
