package com.tp3service.avaliacao.event;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoEvent {
    private Long avaliacaoId;
    private Long mangaId;
    private Integer nota;
    private LocalDateTime avaliadoEm;
}
