package com.spring.investidor.repository;

import com.spring.investidor.model.Investidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestidorRepository extends JpaRepository<Investidor, Long> {
    @Query("select i from Investidor i where i.cnpj = :cnpj")
    Optional<Investidor> findByCnpj(@Param("cnpj") String cnpj);
}
