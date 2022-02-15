package com.spring.investidor.repository;

import com.spring.investidor.model.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {
    @Query("select a from Ativo a where a.nome = :nome")
    Optional<Ativo> findByNome(@Param("nome") String nome);
}
