package com.tp3service.avaliacao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "avaliacoes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "mangaId é obrigatório")
    @Column(nullable = false)
    private Long mangaId;

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é 1")
    @Max(value = 5, message = "Nota máxima é 5")
    @Column(nullable = false)
    private Integer nota;

    @Column(nullable = false)
    private LocalDateTime avaliadoEm;

    @PrePersist
    public void prePersist() {
        this.avaliadoEm = LocalDateTime.now();
    }
}
