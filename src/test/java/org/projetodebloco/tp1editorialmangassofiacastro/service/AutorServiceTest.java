package org.projetodebloco.tp1editorialmangassofiacastro.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.projetodebloco.tp1editorialmangassofiacastro.model.Autor;
import org.projetodebloco.tp1editorialmangassofiacastro.repository.AutorRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @Test
    @DisplayName("Deve listar todos os autores")
    void deveListarTodos() {
        List<Autor> autores = List.of(
                Autor.builder().id(1L).nome("Oda").build(),
                Autor.builder().id(2L).nome("Kishimoto").build()
        );
        when(autorRepository.findAll()).thenReturn(autores);

        List<Autor> resultado = autorService.listarTodos();

        assertThat(resultado).hasSize(2);
        verify(autorRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("Deve lançar exceção quando autor não encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> autorService.buscarPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Autor não encontrado com id: 99");
    }

    @Test
    @DisplayName("Deve criar um autor")
    void deveCriarAutor() {
        Autor autor = Autor.builder().nome("Oda").biografia("Criador de One Piece").build();
        when(autorRepository.save(autor)).thenReturn(
                Autor.builder().id(1L).nome("Oda").biografia("Criador de One Piece").build()
        );

        Autor criado = autorService.criar(autor);

        assertThat(criado.getId()).isEqualTo(1L);
        verify(autorRepository, times(1)).save(autor);
    }
    @Test
    @DisplayName("Deve deletar autor existente")
    void deveDeletarAutor() {
        Autor autor = Autor.builder().id(1L).nome("Oda").build();
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        autorService.deletar(1L);

        verify(autorRepository, times(1)).deleteById(1L);
    }
}
