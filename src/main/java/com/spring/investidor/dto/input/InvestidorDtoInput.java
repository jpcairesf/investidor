package com.spring.investidor.dto.input;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class InvestidorDtoInput {

    @NotBlank
    private String nome;

    @NotBlank @Column(unique = true)
    private String cnpj;

    public InvestidorDtoInput() {}

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
}
