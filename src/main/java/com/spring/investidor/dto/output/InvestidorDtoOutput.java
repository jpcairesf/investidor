package com.spring.investidor.dto.output;

import com.spring.investidor.model.Investidor;

public class InvestidorDtoOutput {

    private Long id;

    private String nome;

    private String cnpj;

    public InvestidorDtoOutput(Investidor investidor) {
        this.id = investidor.getId();
        this.nome = investidor.getNome();
        this.cnpj = investidor.getCnpj();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
