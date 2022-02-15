package com.spring.investidor.service;

import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.MovimentacaoTipo;
import com.spring.investidor.relatorio.ExtratoDto;
import com.spring.investidor.relatorio.MovimentacaoExtratoDto;
import com.spring.investidor.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtratoService {

    private static final Sort sort = Sort.by("data").descending();

    private MovimentacaoRepository movimentacaoRepository;

    private InvestidorService investidorService;

    private AtivoService ativoService;

    @Autowired
    public ExtratoService(
            MovimentacaoRepository movimentacaoRepository,
            InvestidorService investidorService,
            AtivoService ativoService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.investidorService = investidorService;
        this.ativoService = ativoService;
    }

    public ExtratoDto extrato(String investidorCnpj, String ativoNome, String tipo, LocalDate dataRef, Integer periodo) {
        Investidor investidor = investidorService.findByCnpj(investidorCnpj);
        Ativo ativo = ativoService.findByNome(ativoNome);
        MovimentacaoTipo movimentacaoTipo = MovimentacaoTipo.valueOf(tipo);
        List<MovimentacaoExtratoDto> movimentacoes = movimentacaoRepository
                .findByFilter(investidor, ativo, movimentacaoTipo, dataRef, periodo, sort)
                .stream().map(MovimentacaoExtratoDto::new)
                .collect(Collectors.toList());
        ExtratoDto extrato = new ExtratoDto(investidor, movimentacoes);
        return extrato;
    }
}
