package com.spring.investidor.repository.custom;

import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.Movimentacao;
import com.spring.investidor.model.MovimentacaoTipo;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoCustomRepository {

    List<Movimentacao> findByFilter(
            Investidor investidor,
            Ativo ativo,
            MovimentacaoTipo tipo,
            LocalDate dataRef,
            Integer periodo,
            Sort sort);
}
