package com.spring.investidor.service;

import com.spring.investidor.relatorio.VolumeAplicadoProjection;
import com.spring.investidor.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolumeAplicadoService {

    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    public VolumeAplicadoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<VolumeAplicadoProjection> gerarRelatorio() {
        return movimentacaoRepository.findVolumeAplicadoByInvestidor();
    }
}
