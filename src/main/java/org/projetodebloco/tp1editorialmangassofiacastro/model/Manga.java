package org.projetodebloco.tp1editorialmangassofiacastro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "mangas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @NotNull(message = "Gênero é obrigatório")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    private MangaStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
