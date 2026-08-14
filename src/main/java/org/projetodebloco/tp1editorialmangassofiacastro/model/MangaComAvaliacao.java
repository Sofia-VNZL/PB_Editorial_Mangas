package org.projetodebloco.tp1editorialmangassofiacastro.model;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class MangaComAvaliacao {
    private Long id;
    private String titulo;
    private String sinopse;
    private Genero genero;
    private MangaStatus status;
    private String nomeAutor;
    private Double mediaAvaliacoes;
    private Integer totalAvaliacoes;
}
