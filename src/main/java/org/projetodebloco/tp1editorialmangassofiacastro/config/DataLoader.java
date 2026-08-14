package org.projetodebloco.tp1editorialmangassofiacastro.config;

import lombok.RequiredArgsConstructor;
import org.projetodebloco.tp1editorialmangassofiacastro.client.AvaliacaoClient;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Genero;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Manga;
import org.projetodebloco.tp1editorialmangassofiacastro.model.MangaStatus;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.AutorRepository;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.MangaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DataLoader {
    private final AutorRepository autorRepository;
    private final MangaRepository mangaRepository;
    private final AvaliacaoClient avaliacaoClient;

    @Bean
    public CommandLineRunner carregarDados() {
        return args -> {

            if (autorRepository.count() > 0) return;


            Autor oda = autorRepository.save(Autor.builder()
                    .nome("Eiichiro Oda")
                    .biografia("Criador de One Piece, o mangá mais vendido de todos os tempos com mais de 500 milhões de cópias!!!")
                    .build());

            Autor kishimoto = autorRepository.save(Autor.builder()
                    .nome("Masashi Kishimoto")
                    .biografia("O escritor do famoso mangá de ninjas!")
                    .build());

            Autor isayama = autorRepository.save(Autor.builder()
                    .nome("Hajime Isayama")
                    .biografia("Criador de Attack on Titan, série aclamada mundialmente pelo seu enredo complexo e surpreendente (e final polemico né)")
                    .build());
            Autor fujimoto = autorRepository.save(Autor.builder()
                    .nome("Tatsuki Fujimoto")
                    .biografia("Mangká conhecido pelo estilo visual único e narrativas perturbadoras (e personalidade esquisita...)")
                    .build());

            Autor murata = autorRepository.save(Autor.builder()
                    .nome("Yusuke Murata")
                    .biografia("Ilustrador de One Punch Man, reconhecido pelo nível técnico extraordinário dos seus desenhos")
                    .build());


            Manga onePiece = mangaRepository.save(Manga.builder()
                    .titulo("One Piece")
                    .sinopse("A jornada de Monkey D. Luffy e a sua tripulação em busca do tesouro lendário One Piece, para que Luffy se torne o Rei dos Piratas.")
                    .genero(Genero.SHONEN)
                    .status(MangaStatus.EM_PUBLICACAO)
                    .autor(oda)
                    .build());

            Manga naruto = mangaRepository.save(Manga.builder()
                    .titulo("Naruto")
                    .sinopse("A história de Naruto Uzumaki, um jovem ninja com o sonho de se tornar o Hokage, o líder mais poderoso da sua aldeia.")
                    .genero(Genero.SHONEN)
                    .status(MangaStatus.CONCLUIDO)
                    .autor(kishimoto)
                    .build());
            Manga aot = mangaRepository.save(Manga.builder()
                    .titulo("Attack on Titan")
                    .sinopse("A humanidade vive atrás de muros gigantes para se proteger dos Titans, criaturas humanoides que devoram pessoas.")
                    .genero(Genero.SEINEN)
                    .status(MangaStatus.CONCLUIDO)
                    .autor(isayama)
                    .build());

            Manga chainsawMan = mangaRepository.save(Manga.builder()
                    .titulo("Chainsaw Man")
                    .sinopse("Denji é um jovem caçador de demónios que se funde com o seu cão demônio Pochita para se tornar o Chainsaw Man.")
                    .genero(Genero.SEINEN)
                    .status(MangaStatus.EM_PUBLICACAO)
                    .autor(fujimoto)
                    .build());

            Manga opm = mangaRepository.save(Manga.builder()
                    .titulo("One Punch Man")
                    .sinopse("Saitama é um herói que pode derrotar qualquer inimigo com um único soco, mas isso tornou a sua vida completamente sem graça.")
                    .genero(Genero.SHONEN)
                    .status(MangaStatus.EM_PUBLICACAO)
                    .autor(murata)
                    .build());

            criarAvaliacao(onePiece.getId(), 5);
            criarAvaliacao(onePiece.getId(), 5);
            criarAvaliacao(onePiece.getId(), 4);

            criarAvaliacao(naruto.getId(), 4);
            criarAvaliacao(naruto.getId(), 5);

            criarAvaliacao(aot.getId(), 5);
            criarAvaliacao(aot.getId(), 5);
            criarAvaliacao(aot.getId(), 4);

            criarAvaliacao(chainsawMan.getId(), 4);
            criarAvaliacao(chainsawMan.getId(), 5);

            criarAvaliacao(opm.getId(), 5);
            criarAvaliacao(opm.getId(), 4);

            System.out.println("Dados base carregados com sucesso!!!");
        };
    }
    private void criarAvaliacao(Long mangaId, int nota) {
        try {
            avaliacaoClient.criarAvaliacao(mangaId, nota);
        } catch (Exception e) {
            System.out.println("avaliacao-service indisponível /// avaliação não criada para mangaId: " + mangaId);
        }
    }
}