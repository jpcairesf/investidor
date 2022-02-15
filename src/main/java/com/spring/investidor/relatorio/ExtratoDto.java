package com.spring.investidor.relatorio;

import com.spring.investidor.model.Investidor;

import java.util.List;

public class ExtratoDto {

    private String nome;

    private String cnpj;

    private List<MovimentacaoExtratoDto> movimentacoes;

    public ExtratoDto(Investidor investidor, List<MovimentacaoExtratoDto> movimentacoes) {
        this.nome = investidor.getNome();
        this.cnpj = investidor.getCnpj();
        this.movimentacoes = movimentacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<MovimentacaoExtratoDto> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoExtratoDto> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}
