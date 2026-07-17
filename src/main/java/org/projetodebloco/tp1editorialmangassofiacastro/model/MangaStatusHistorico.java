package org.projetodebloco.tp1editorialmangassofiacastro.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "manga_status_historico")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class MangaStatusHistorico {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mangaId;

    @Column(nullable = false)
    private String tituloManga;

    private Long autorId;

    private String nomeAutor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MangaStatus statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MangaStatus statusNovo;

    @Column(nullable = false)
    private LocalDateTime alteradoEm;

    @PrePersist
    public void prePersist() {
        this.alteradoEm = LocalDateTime.now();
    }
}
