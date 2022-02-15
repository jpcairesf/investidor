package com.spring.investidor.service;

import com.spring.investidor.dto.input.AtivoDtoInput;
import com.spring.investidor.dto.output.AtivoDtoOutput;
import com.spring.investidor.model.Ativo;
import com.spring.investidor.repository.AtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AtivoService {


    private AtivoRepository ativoRepository;

    @Autowired
    public AtivoService(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

    public List<AtivoDtoOutput> findAll() {
        List<AtivoDtoOutput> ativoDtoOutputs = ativoRepository
                .findAll().stream()
                .map(AtivoDtoOutput::new)
                .collect(Collectors.toList());
        return ativoDtoOutputs;
    }

    public AtivoDtoOutput findById(Long id) {
        Optional<Ativo> ativo = ativoRepository.findById(id);
        if (ativo.isPresent()) {
            AtivoDtoOutput ativoDtoOutput = new AtivoDtoOutput(ativo.get());
            return ativoDtoOutput;
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public Ativo findByNome(String nome) {
        Optional<Ativo> ativo = ativoRepository.findByNome(nome);
        if (ativo.isPresent()) {
            return ativo.get();
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public AtivoDtoOutput save(AtivoDtoInput ativoDtoInput) {
        if (!ativoRepository.findByNome(ativoDtoInput.getNome()).isPresent()) {
            Ativo ativo = toAtivo(ativoDtoInput);
            ativoRepository.save(ativo);
            return new AtivoDtoOutput(ativo);
        }
        throw new IllegalArgumentException("Já existe um ativo com esse nome");
    }

    public AtivoDtoOutput updateById(AtivoDtoInput ativoDtoInput, Long id) {
        Optional<Ativo> ativo = ativoRepository.findById(id);
        if (ativo.isPresent()) {
            update(ativoDtoInput, ativo.get());
            ativoRepository.save(ativo.get());
            return new AtivoDtoOutput(ativo.get());
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public void deleteById(Long id) {
        Optional<Ativo> ativo = ativoRepository.findById(id);
        if (ativo.isPresent()) {
            ativoRepository.deleteById(id);
            return;
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public void update(AtivoDtoInput ativoDtoInput, Ativo ativo) {
        ativo.setNome(ativoDtoInput.getNome());
    }

    public Ativo toAtivo(AtivoDtoInput ativoDtoInput) {
        Ativo ativo = new Ativo();
        update(ativoDtoInput, ativo);
        return ativo;
    }
}
