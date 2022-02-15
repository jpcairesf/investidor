package com.spring.investidor.service;

import com.spring.investidor.dto.input.MovimentacaoDtoInput;
import com.spring.investidor.dto.output.MovimentacaoDtoOutput;
import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.Movimentacao;
import com.spring.investidor.model.MovimentacaoTipo;
import com.spring.investidor.repository.MovimentacaoRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovimentacaoServiceTest {

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private AtivoService ativoService;

    @Mock
    private InvestidorService investidorService;

    @InjectMocks
    private MovimentacaoService movimentacaoService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldSaveAplicacaoCorrectly() {
        Investidor investidor = newInvestidor();
        Ativo ativo = newAtivo();

        when(investidorService.findByCnpj("123456789")).thenReturn(investidor);
        when(ativoService.findByNome("Nome Ativo")).thenReturn(ativo);

        Movimentacao expected = newMovimentacao(MovimentacaoTipo.APLICACAO, investidor, ativo, BigDecimal.TEN);
        MovimentacaoDtoOutput expectedOutput = new MovimentacaoDtoOutput(expected);

        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(new Movimentacao());

        MovimentacaoDtoInput input = newMovimentacaoDtoInput(BigDecimal.TEN);

        MovimentacaoDtoOutput output = movimentacaoService.aplicar(input);
        output.setId(1L);

        assertMovimentacao(output, expectedOutput);
    }

    @Test
    public void shouldSaveResgateCorrectly() {
        Investidor investidor = newInvestidor();
        Ativo ativo = newAtivo();

        when(investidorService.findByCnpj("123456789")).thenReturn(investidor);
        when(ativoService.findByNome("Nome Ativo")).thenReturn(ativo);

        Movimentacao expected = newMovimentacao(MovimentacaoTipo.RESGATE, investidor, ativo, BigDecimal.ONE);
        MovimentacaoDtoOutput expectedOutput = new MovimentacaoDtoOutput(expected);

        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(new Movimentacao());
        when(movimentacaoRepository.findTotalByInvestidorAndAtivo(investidor, ativo)).thenReturn(BigDecimal.TEN);

        MovimentacaoDtoInput input = newMovimentacaoDtoInput(BigDecimal.ONE);

        MovimentacaoDtoOutput output = movimentacaoService.resgatar(input);
        output.setId(1L);

        assertMovimentacao(output, expectedOutput);
    }

    @Test
    public void shouldThrowSaldoIndisponivelParaResgate() {

    }

    @Test
    public void shouldReturnAllMovimentacoes() {
        Investidor investidor = newInvestidor();
        Ativo ativo = newAtivo();
        Movimentacao movimentacao = newMovimentacao(MovimentacaoTipo.APLICACAO, investidor, ativo, BigDecimal.ONE);

        List<Movimentacao> movimentacaoList = new ArrayList<>();
        movimentacaoList.add(movimentacao);

        MovimentacaoDtoOutput output = new MovimentacaoDtoOutput(movimentacao);
        List<MovimentacaoDtoOutput> expectedOutputList = new ArrayList<>();
        expectedOutputList.add(output);

        when(movimentacaoRepository.findAll()).thenReturn(movimentacaoList);

        List<MovimentacaoDtoOutput> outputList = movimentacaoService.findAll();

        verify(movimentacaoRepository).findAll();
        assertMovimentacaoList(outputList, expectedOutputList);
    }

    @Test
    public void shouldSearchMovimentacaoByCnpjAtivoTipoAndData() {
        Sort sort = Sort.by("data").descending();

        Investidor investidor = newInvestidor();
        Ativo ativo = newAtivo();
        Movimentacao movimentacao = newMovimentacao(MovimentacaoTipo.APLICACAO, investidor, ativo, BigDecimal.ONE);

        when(investidorService.findByCnpj("123456789")).thenReturn(investidor);
        when(ativoService.findByNome("Nome Ativo")).thenReturn(ativo);

        List<Movimentacao> movimentacaoList = new ArrayList<>();
        movimentacaoList.add(movimentacao);

        LocalDate dataInicio = LocalDate.of(2022, 1, 1);
        LocalDate dataFim = LocalDate.of(2023, 1, 1);

        MovimentacaoDtoOutput output = new MovimentacaoDtoOutput(movimentacao);
        List<MovimentacaoDtoOutput> expectedOutputList = new ArrayList<>();
        expectedOutputList.add(output);

        when(movimentacaoRepository.findAllByInvestidorAndAtivoAndTipoAndDataBetween(
                investidor,
                ativo,
                MovimentacaoTipo.APLICACAO,
                dataInicio,
                dataFim,
                sort
        )).thenReturn(movimentacaoList);

        List<MovimentacaoDtoOutput> outputList = movimentacaoService.buscar(
                "123456789",
                "Nome Ativo",
                "APLICACAO",
                dataInicio,
                dataFim
        );

        verify(movimentacaoRepository).findAllByInvestidorAndAtivoAndTipoAndDataBetween(
                investidor,
                ativo,
                MovimentacaoTipo.APLICACAO,
                dataInicio,
                dataFim,
                sort
        );
        assertMovimentacaoList(outputList, expectedOutputList);
    }

    private Ativo newAtivo() {
        Ativo ativo = new Ativo();
        ativo.setId(1L);
        ativo.setNome("Nome Ativo");
        return ativo;
    }

    private Investidor newInvestidor() {
        Investidor investidor = new Investidor();
        investidor.setId(1L);
        investidor.setNome("Nome Investidor");
        investidor.setCnpj("123456789");
        return investidor;
    }

    private Movimentacao newMovimentacao(MovimentacaoTipo tipo, Investidor investidor, Ativo ativo, BigDecimal quantidade) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(1L);
        movimentacao.setTipo(tipo);
        movimentacao.setData(LocalDate.now());
        movimentacao.setInvestidor(investidor);
        movimentacao.setAtivo(ativo);
        movimentacao.setQuantidade(quantidade);
        return movimentacao;
    }

    private MovimentacaoDtoInput newMovimentacaoDtoInput(BigDecimal quantidade) {
        MovimentacaoDtoInput input = new MovimentacaoDtoInput();
        input.setCnpj("123456789");
        input.setAtivo("Nome Ativo");
        input.setQuantidade(quantidade);
        return input;
    }

    private void assertMovimentacao(MovimentacaoDtoOutput output, MovimentacaoDtoOutput expectedOutput) {
        verify(movimentacaoRepository).save(any(Movimentacao.class));
        verify(investidorService).findByCnpj("123456789");
        verify(ativoService).findByNome("Nome Ativo");
        assertThat(output.getId()).isSameAs(expectedOutput.getId());
        assertThat(output.getAtivoId()).isSameAs(expectedOutput.getAtivoId());
        assertThat(output.getInvestidorId()).isSameAs(expectedOutput.getInvestidorId());
        assertThat(output.getData().toString()).isEqualTo(expectedOutput.getData().toString());
        assertThat(output.getQuantidade()).isSameAs(expectedOutput.getQuantidade());
        assertThat(output.getTipo()).isSameAs(expectedOutput.getTipo());
    }

    private void assertMovimentacaoList(List<MovimentacaoDtoOutput> outputList, List<MovimentacaoDtoOutput> expectedOutputList) {
        assertThat(outputList.size()).isSameAs(expectedOutputList.size());
        assertThat(outputList.get(0).getId()).isSameAs(expectedOutputList.get(0).getId());
        assertThat(outputList.get(0).getInvestidorId()).isSameAs(expectedOutputList.get(0).getInvestidorId());
        assertThat(outputList.get(0).getAtivoId()).isSameAs(expectedOutputList.get(0).getAtivoId());
        assertThat(outputList.get(0).getTipo()).isSameAs(expectedOutputList.get(0).getTipo());
        assertThat(outputList.get(0).getQuantidade()).isSameAs(expectedOutputList.get(0).getQuantidade());
        assertThat(outputList.get(0).getData()).isSameAs(expectedOutputList.get(0).getData());
    }
}
