package com.spring.investidor.dto.output;

import com.spring.investidor.model.Ativo;

public class AtivoDtoOutput {

    private Long id;

    private String nome;

    public AtivoDtoOutput(Ativo ativo) {
        this.id = ativo.getId();
        this.nome = ativo.getNome();
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
