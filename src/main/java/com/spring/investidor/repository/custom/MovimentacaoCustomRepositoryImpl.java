package com.spring.investidor.repository.custom;

import com.spring.investidor.model.Ativo;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.model.Movimentacao;
import com.spring.investidor.model.MovimentacaoTipo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class
MovimentacaoCustomRepositoryImpl implements MovimentacaoCustomRepository {

    private EntityManager em;

    public MovimentacaoCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Movimentacao> findByFilter(
            Investidor investidor,
            Ativo ativo,
            MovimentacaoTipo tipo,
            LocalDate dataRef,
            Integer periodo,
            Sort sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movimentacao> cq = cb.createQuery(Movimentacao.class);
        Root<Movimentacao> movimentacaoRoot = cq.from(Movimentacao.class);
        List<Predicate> predicates = new ArrayList<>();

        LocalDate dataInicio = dataRef.minusDays(periodo);

        predicates.add(cb.equal(movimentacaoRoot.get("investidor"), investidor));
        predicates.add(cb.between(movimentacaoRoot.get("data"), dataInicio, dataRef));

        if (ativo != null) {
            predicates.add(cb.equal(movimentacaoRoot.get("ativo"), ativo));
        }

        if (tipo != null) {
            predicates.add(cb.equal(movimentacaoRoot.get("tipo"), tipo));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }
}
