package com.spring.investidor.dto.input;

import javax.validation.constraints.NotBlank;

public class AtivoDtoInput {

    @NotBlank
    private String nome;

    public AtivoDtoInput() {}

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }
}
