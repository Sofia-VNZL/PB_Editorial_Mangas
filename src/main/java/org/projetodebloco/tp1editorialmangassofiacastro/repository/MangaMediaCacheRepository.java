package org.projetodebloco.tp1editorialmangassofiacastro.repository;

import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaMediaCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaMediaCacheRepository extends JpaRepository<MangaMediaCache, Long> {
}
