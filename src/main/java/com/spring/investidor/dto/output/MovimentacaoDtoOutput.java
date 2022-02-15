package com.spring.investidor.dto.output;

import com.spring.investidor.model.Movimentacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovimentacaoDtoOutput {

    private Long id;

    private Long ativoId;

    private Long investidorId;

    private String tipo;

    private LocalDate data;

    private BigDecimal quantidade;

    public MovimentacaoDtoOutput(Movimentacao movimentacao) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        this.id = movimentacao.getId();
        this.ativoId = movimentacao.getAtivo().getId();
        this.investidorId = movimentacao.getInvestidor().getId();
        this.tipo = movimentacao.getTipo().toString();
        this.data = movimentacao.getData();
        this.quantidade = movimentacao.getQuantidade().abs();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAtivoId() {
        return ativoId;
    }

    public void setAtivoId(Long ativoId) {
        this.ativoId = ativoId;
    }

    public Long getInvestidorId() {
        return investidorId;
    }

    public void setInvestidorId(Long investidorId) {
        this.investidorId = investidorId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
