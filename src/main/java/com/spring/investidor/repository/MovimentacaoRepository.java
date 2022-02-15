package com.spring.investidor.repository;

import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.Movimentacao;
import com.spring.investidor.model.MovimentacaoTipo;
import com.spring.investidor.relatorio.VolumeAplicadoProjection;
import com.spring.investidor.repository.custom.MovimentacaoCustomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long>, MovimentacaoCustomRepository {

    List<Movimentacao> findAllByInvestidorAndAtivoAndTipoAndDataBetween(
            Investidor investidor,
            Ativo ativo,
            MovimentacaoTipo tipo,
            LocalDate dataInicio,
            LocalDate dataFim,
            Sort sort);

    @Query(value = "select coalesce(sum(quantidade),0) from Movimentacao m " +
            "where m.investidor = :investidor " +
            "and m.ativo = :ativo ")
    BigDecimal findTotalByInvestidorAndAtivo(
            @Param("investidor") Investidor investidor,
            @Param("ativo") Ativo ativo);

    @Query(value = "select i.nome as nome, i.cnpj as cnpj, " +
            "(coalesce(sum(m.quantidade),0)) as volume from Movimentacao m " +
            "join m.investidor i " +
            "where m.tipo = 'APLICACAO' " +
            "group by cnpj, nome " +
            "order by volume desc")
    List<VolumeAplicadoProjection> findVolumeAplicadoByInvestidor();
}
