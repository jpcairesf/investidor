package com.spring.investidor.service;

import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.Movimentacao;
import com.spring.investidor.model.MovimentacaoTipo;
import com.spring.investidor.relatorio.ExtratoDto;
import com.spring.investidor.relatorio.MovimentacaoExtratoDto;
import com.spring.investidor.repository.MovimentacaoRepository;
import org.junit.Test;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtratoServiceTest {
    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private InvestidorService investidorService;

    @Mock
    private AtivoService ativoService;

    @InjectMocks
    private ExtratoService extratoService;

    @Test
    public void shouldReturnExtratoCorrectly() {
        Sort sort = Sort.by("data").descending();

        Investidor investidor = new Investidor();
        investidor.setId(1L);
        investidor.setNome("Nome Investidor");
        investidor.setCnpj("123456789");

        Ativo ativo = new Ativo();
        ativo.setId(1L);
        ativo.setNome("Nome Ativo");

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(1L);
        movimentacao.setTipo(MovimentacaoTipo.APLICACAO);
        movimentacao.setData(LocalDate.now());
        movimentacao.setInvestidor(investidor);
        movimentacao.setAtivo(ativo);
        movimentacao.setQuantidade(BigDecimal.TEN);

        List<Movimentacao> movimentacaoList = new ArrayList<>();
        movimentacaoList.add(movimentacao);

        MovimentacaoExtratoDto movimentacaoExtratoDto = new MovimentacaoExtratoDto(movimentacao);
        List<MovimentacaoExtratoDto> movimentacaoExtratoDtoList = new ArrayList<>();
        movimentacaoExtratoDtoList.add(movimentacaoExtratoDto);

        ExtratoDto expectedExtratoDto = new ExtratoDto(investidor, movimentacaoExtratoDtoList);

        LocalDate dataRef = LocalDate.of(2022, 1, 1);
        int periodo = 10;

        when(investidorService.findByCnpj("123456789")).thenReturn(investidor);
        when(ativoService.findByNome("Nome Ativo")).thenReturn(ativo);
        when(movimentacaoRepository.findByFilter(
                investidor,
                ativo,
                MovimentacaoTipo.APLICACAO,
                dataRef,
                periodo,
                sort)).thenReturn(movimentacaoList);

        ExtratoDto extratoDto = extratoService.extrato(
                "123456789",
                "Nome Ativo",
                "APLICACAO",
                dataRef,
                periodo);

        verify(investidorService).findByCnpj("123456789");
        verify(ativoService).findByNome("Nome Ativo");
        verify(movimentacaoRepository).findByFilter(
                investidor,
                ativo,
                MovimentacaoTipo.APLICACAO,
                dataRef,
                periodo,
                sort);
        assertThat(extratoDto.getCnpj()).isSameAs(expectedExtratoDto.getCnpj());
        assertThat(extratoDto.getNome()).isSameAs(expectedExtratoDto.getNome());
        assertThat(extratoDto.getMovimentacoes().get(0).getData().toString())
                .isEqualTo(expectedExtratoDto.getMovimentacoes().get(0).getData().toString());
        assertThat(extratoDto.getMovimentacoes().get(0).getQuantidade())
                .isSameAs(expectedExtratoDto.getMovimentacoes().get(0).getQuantidade());
        assertThat(extratoDto.getMovimentacoes().get(0).getTipo())
                .isSameAs(expectedExtratoDto.getMovimentacoes().get(0).getTipo());
        assertThat(extratoDto.getMovimentacoes().get(0).getAtivoNome())
                .isSameAs(expectedExtratoDto.getMovimentacoes().get(0).getAtivoNome());
    }
}
