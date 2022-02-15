package com.spring.investidor.service;

import com.spring.investidor.dto.input.AtivoDtoInput;
import com.spring.investidor.dto.output.AtivoDtoOutput;
import com.spring.investidor.model.Ativo;
import com.spring.investidor.repository.AtivoRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtivoServiceTest {

    @Mock
    private AtivoRepository ativoRepository;

    @InjectMocks
    private AtivoService ativoService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldReturnAllAtivos() {
        Ativo ativo = newAtivo(new Ativo(), "Nome");
        List<Ativo> ativoList = new ArrayList<>();
        ativoList.add(ativo);

        AtivoDtoOutput output = new AtivoDtoOutput(ativo);
        List<AtivoDtoOutput> expectedOutputList = new ArrayList<>();
        expectedOutputList.add(output);

        when(ativoRepository.findAll()).thenReturn(ativoList);

        List<AtivoDtoOutput> outputList = ativoService.findAll();

        verify(ativoRepository).findAll();
        assertThat(outputList.size()).isSameAs(expectedOutputList.size());
        assertThat(outputList.get(0).getId()).isSameAs(expectedOutputList.get(0).getId());
        assertThat(outputList.get(0).getNome()).isSameAs(expectedOutputList.get(0).getNome());
    }

    @Test
//    implementar assertThrows
    public void shouldNotFindByInvalidId() {
//        when(ativoRepository.findById(2L)).thenReturn(Optional.empty());
    }

    @Test
    public void shouldSaveCorrectly() {
        AtivoDtoInput input = new AtivoDtoInput();
        input.setNome("Nome");

        when(ativoRepository.save(any(Ativo.class))).thenReturn(new Ativo());

        AtivoDtoOutput output = ativoService.save(input);

        verify(ativoRepository).save(any(Ativo.class));
        assertThat(output.getNome()).isSameAs(input.getNome());
    }

    @Test
//    implementar assertThrows
    public void shouldThrowNomeInvalido() {
        Optional<Ativo> ativoOptional = Optional.ofNullable(new Ativo());
        Ativo ativo = newAtivo(ativoOptional.get(), "Nome");

        AtivoDtoInput input = new AtivoDtoInput();
        input.setNome("Nome");

//        when(ativoRepository.findByNome("Nome")).thenReturn(ativoOptional);

//        ativoService.save(input);
    }

    @Test
    public void shouldUpdateCorrectly() {
        AtivoDtoInput input = new AtivoDtoInput();
        input.setNome("Nome novo");

        Ativo expected = newAtivo(new Ativo(), "Nome novo");
        AtivoDtoOutput expectedOutput = new AtivoDtoOutput(expected);

        Optional<Ativo> ativoOptional = Optional.ofNullable(new Ativo());
        Ativo ativo = newAtivo(ativoOptional.get(), "Nome");

        when(ativoRepository.findById(1L)).thenReturn(ativoOptional);

        AtivoDtoOutput output = ativoService.updateById(input, 1L);

        assertThat(output.getId()).isSameAs(expectedOutput.getId());
        assertThat(output.getNome()).isSameAs(expectedOutput.getNome());
    }

    @Test
    public void shouldDeleteCorrectly() {
        Optional<Ativo> ativoOptional = Optional.ofNullable(new Ativo());
        Ativo ativo = newAtivo(ativoOptional.get(), "Nome");

        when(ativoRepository.findById(1L)).thenReturn(ativoOptional);

        ativoService.deleteById(1L);

        verify(ativoRepository).deleteById(ativo.getId());
    }

    private Ativo newAtivo(Ativo ativo, String Nome) {
        ativo.setId(1L);
        ativo.setNome(Nome);
        return ativo;
    }
}
