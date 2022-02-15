package com.spring.investidor.relatorio;

import com.spring.investidor.model.Movimentacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimentacaoExtratoDto {

    private String ativoNome;

    private String tipo;

    private BigDecimal quantidade;

    private LocalDate data;

    public MovimentacaoExtratoDto(Movimentacao movimentacao) {
        this.ativoNome = movimentacao.getAtivo().getNome();
        this.tipo = movimentacao.getTipo().toString();
        this.quantidade = movimentacao.getQuantidade();
        this.data = movimentacao.getData();
    }

    public String getAtivoNome() {
        return ativoNome;
    }

    public void setAtivoNome(String ativoNome) {
        this.ativoNome = ativoNome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
