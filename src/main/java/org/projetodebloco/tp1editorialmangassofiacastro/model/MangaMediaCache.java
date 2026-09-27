package org.projetodebloco.tp1editorialmangassofiacastro.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "manga_media_cache")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MangaMediaCache {
    @Id
    private Long mangaId;

    private Double mediaAvaliacoes;
    private Integer totalAvaliacoes;
}
