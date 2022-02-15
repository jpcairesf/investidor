package com.spring.investidor.service;

import com.spring.investidor.dto.input.InvestidorDtoInput;
import com.spring.investidor.dto.output.InvestidorDtoOutput;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.repository.InvestidorRepository;
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
public class InvestidorServiceTest {

    @Mock
    private InvestidorRepository investidorRepository;

    @InjectMocks
    private InvestidorService investidorService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldReturnAllInvestidores() {
        Investidor investidor = newInvestidor(new Investidor(),"Nome", "123456789");
        List<Investidor> investidorList = new ArrayList<>();
        investidorList.add(investidor);

        InvestidorDtoOutput output = new InvestidorDtoOutput(investidor);
        List<InvestidorDtoOutput> expectedOutputList = new ArrayList<>();
        expectedOutputList.add(output);

        when(investidorRepository.findAll()).thenReturn(investidorList);

        List<InvestidorDtoOutput> outputList = investidorService.findAll();

        verify(investidorRepository).findAll();
        assertThat(outputList.size()).isSameAs(expectedOutputList.size());
        assertThat(outputList.get(0).getId()).isSameAs(expectedOutputList.get(0).getId());
        assertThat(outputList.get(0).getCnpj()).isSameAs(expectedOutputList.get(0).getCnpj());
        assertThat(outputList.get(0).getNome()).isSameAs(expectedOutputList.get(0).getNome());
    }

    @Test
//    implementar assertThrows
    public void shouldNotFindByInvalidId() {
//        when(investidorRepository.findById(2L)).thenReturn(Optional.empty());
    }

    @Test
//    implementar assertThrows
    public void shouldNotFindByInvalidCnpj() {
//        when(investidorRepository.findByCnpj("111222333")).thenReturn(Optional.empty());
    }

    @Test
    public void shouldSaveCorrectly() {
        InvestidorDtoInput input = newInvestidorDtoInput("Nome", "123456789");

        when(investidorRepository.save(any(Investidor.class))).thenReturn(new Investidor());

        InvestidorDtoOutput output = investidorService.save(input);

        verify(investidorRepository).save(any(Investidor.class));
        assertThat(output.getCnpj()).isSameAs(input.getCnpj());
        assertThat(output.getNome()).isSameAs(input.getNome());
    }

    @Test
//    implementar assertThrows
    public void shouldThrowCnpjInvalido() {
        Optional<Investidor> investidorOptional = Optional.ofNullable(new Investidor());
        Investidor investidor = newInvestidor(investidorOptional.get(), "Nome 1", "123456789");

        newInvestidorDtoInput("Nome 2", "123456789");

//        when(investidorRepository.findByCnpj("123456789")).thenReturn(investidorOptional);

//        investidorService.save(input);
    }

    @Test
    public void shouldUpdateCorrectly() {
        InvestidorDtoInput input = newInvestidorDtoInput("Nome novo", "111222333");

        Investidor expected = newInvestidor(new Investidor(),"Nome novo", "111222333");
        InvestidorDtoOutput expectedOutput = new InvestidorDtoOutput(expected);

        Optional<Investidor> investidorOptional = Optional.ofNullable(new Investidor());
        Investidor investidor = newInvestidor(investidorOptional.get(), "Nome", "123456789");

        when(investidorRepository.findById(1L)).thenReturn(investidorOptional);

        InvestidorDtoOutput output = investidorService.updateById(input, 1L);

        assertThat(output.getId()).isSameAs(expectedOutput.getId());
        assertThat(output.getCnpj()).isSameAs(expectedOutput.getCnpj());
        assertThat(output.getNome()).isSameAs(expectedOutput.getNome());
    }

    @Test
    public void shouldDeleteCorrectly() {
        Optional<Investidor> investidorOptional = Optional.ofNullable(new Investidor());
        Investidor investidor = newInvestidor(investidorOptional.get(), "Nome", "123456789");

        when(investidorRepository.findById(1L)).thenReturn(investidorOptional);

        investidorService.deleteById(1L);

        verify(investidorRepository).deleteById(investidor.getId());
    }

    private Investidor newInvestidor(Investidor investidor, String Nome, String cnpj) {
        investidor.setId(1L);
        investidor.setNome(Nome);
        investidor.setCnpj(cnpj);
        return investidor;
    }

    private InvestidorDtoInput newInvestidorDtoInput(String nome, String cnpj) {
        InvestidorDtoInput input = new InvestidorDtoInput();
        input.setNome(nome);
        input.setCnpj(cnpj);
        return input;
    }

}
