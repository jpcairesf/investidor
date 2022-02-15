package com.spring.investidor.service;

import com.spring.investidor.dto.input.InvestidorDtoInput;
import com.spring.investidor.dto.output.InvestidorDtoOutput;
import com.spring.investidor.model.Investidor;
import com.spring.investidor.repository.InvestidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvestidorService {

    private InvestidorRepository investidorRepository;

    @Autowired
    public InvestidorService(InvestidorRepository investidorRepository) {
        this.investidorRepository = investidorRepository;
    }

    public List<InvestidorDtoOutput> findAll() {
        List<InvestidorDtoOutput> outputs = investidorRepository.findAll().stream()
                .map(InvestidorDtoOutput::new)
                .collect(Collectors.toList());
        return outputs;
    }

    public InvestidorDtoOutput findById(Long id) {
        Optional<Investidor> investidor = investidorRepository.findById(id);
        if (investidor.isPresent()) {
            InvestidorDtoOutput output = new InvestidorDtoOutput(investidor.get());
            return output;
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public Investidor findByCnpj(String cnpj) {
        Optional<Investidor> investidor = investidorRepository.findByCnpj(cnpj);
        if (investidor.isPresent()) {
            return investidor.get();
        }
        throw new IllegalArgumentException("Cnpj inválido");
    }

    public InvestidorDtoOutput save(InvestidorDtoInput input) {
        if (!investidorRepository.findByCnpj(input.getCnpj()).isPresent()) {
            Investidor investidor = toInvestidor(input);
            investidorRepository.save(investidor);
            InvestidorDtoOutput output = new InvestidorDtoOutput(investidor);
            return output;
        } throw new IllegalArgumentException("Já existe um investidor com esse CNPJ");
    }

    public InvestidorDtoOutput updateById(InvestidorDtoInput input, Long id) {
        Optional<Investidor> investidor = investidorRepository.findById(id);
        if (investidor.isPresent()) {
            update(input, investidor.get());
            investidorRepository.save(investidor.get());
            InvestidorDtoOutput output = new InvestidorDtoOutput(investidor.get());
            return output;
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public void deleteById(Long id) {
        Optional<Investidor> investidor = investidorRepository.findById(id);
        if (investidor.isPresent()) {
            investidorRepository.deleteById(id);
            return;
        }
        throw new IllegalArgumentException("Id inválido");
    }

    public void update(InvestidorDtoInput input, Investidor investidor) {
        investidor.setCnpj(input.getCnpj());
        investidor.setNome(input.getNome());
    }

    public Investidor toInvestidor(InvestidorDtoInput input) {
        Investidor investidor = new Investidor();
        update(input, investidor);
        return investidor;
    }
}
