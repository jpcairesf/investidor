package com.spring.investidor.service;

import com.spring.investidor.dto.input.MovimentacaoDtoInput;
import com.spring.investidor.dto.output.MovimentacaoDtoOutput;
import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.Movimentacao;
import com.spring.investidor.model.MovimentacaoTipo;
import com.spring.investidor.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    private static final Sort sort = Sort.by("data").descending();

    private MovimentacaoRepository movimentacaoRepository;

    private AtivoService ativoService;

    private InvestidorService investidorService;

    @Autowired
    public MovimentacaoService(
            MovimentacaoRepository movimentacaoRepository,
            AtivoService ativoService,
            InvestidorService investidorService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.ativoService = ativoService;
        this.investidorService = investidorService;
    }

    public MovimentacaoDtoOutput aplicar(MovimentacaoDtoInput input) {
        System.out.println(input.toString());
        Movimentacao movimentacao = toMovimentacao(input, MovimentacaoTipo.APLICACAO);
        movimentacaoRepository.save(movimentacao);
        MovimentacaoDtoOutput output = new MovimentacaoDtoOutput(movimentacao);
        return output;
    }

    public MovimentacaoDtoOutput resgatar(MovimentacaoDtoInput input) {
        Movimentacao movimentacao = toMovimentacao(input, MovimentacaoTipo.RESGATE);
        movimentacaoRepository.save(movimentacao);
        MovimentacaoDtoOutput output = new MovimentacaoDtoOutput(movimentacao);
        return output;
    }

    public List<MovimentacaoDtoOutput> findAll() {
        List<MovimentacaoDtoOutput> outputs = movimentacaoRepository
                .findAll().stream()
                .map(MovimentacaoDtoOutput::new)
                .collect(Collectors.toList());
        return outputs;
    }

    public List<MovimentacaoDtoOutput> buscar(
            String investidorCnpj,
            String ativoNome,
            String tipo,
            LocalDate dataInicio,
            LocalDate dataFim) {
        Investidor investidor = investidorService.findByCnpj(investidorCnpj);
        Ativo ativo = ativoService.findByNome(ativoNome);
        MovimentacaoTipo movimentacaoTipo = MovimentacaoTipo.valueOf(tipo.toUpperCase());
        List<MovimentacaoDtoOutput> outputs = movimentacaoRepository
                .findAllByInvestidorAndAtivoAndTipoAndDataBetween(
                        investidor, ativo, movimentacaoTipo, dataInicio, dataFim, sort)
                .stream().map(MovimentacaoDtoOutput::new)
                .collect(Collectors.toList());
        return outputs;
    }

    public boolean validarResgate(MovimentacaoDtoInput input, Investidor investidor, Ativo ativo) {
        BigDecimal total = movimentacaoRepository.findTotalByInvestidorAndAtivo(investidor, ativo);
        return input.getQuantidade().compareTo(total) == -1;
    }

    public Movimentacao toMovimentacao(MovimentacaoDtoInput input, MovimentacaoTipo tipo) {
        Movimentacao movimentacao = new Movimentacao();
        Investidor investidor = investidorService.findByCnpj(input.getCnpj());
        Ativo ativo = ativoService.findByNome(input.getAtivo());
        movimentacao.setInvestidor(investidor);
        movimentacao.setAtivo(ativo);
        movimentacao.setData(LocalDate.now());
        movimentacao.setTipo(tipo);
        if (tipo == MovimentacaoTipo.APLICACAO) {
            movimentacao.setQuantidade(input.getQuantidade());
            return movimentacao;
        } else if (tipo == MovimentacaoTipo.RESGATE && validarResgate(input, investidor, ativo)) {
            movimentacao.setQuantidade(input.getQuantidade().negate());
            return movimentacao;
        }
        throw new IllegalArgumentException("Saldo indisponível para resgate");
    }
}
