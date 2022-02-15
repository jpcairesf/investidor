package com.spring.investidor.controller;

import com.spring.investidor.relatorio.VolumeAplicadoProjection;
import com.spring.investidor.service.VolumeAplicadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/volume-aplicado")
public class VolumeAplicadoController {

    private VolumeAplicadoService volumeAplicadoService;

    @Autowired
    public VolumeAplicadoController(VolumeAplicadoService volumeAplicadoService) {
        this.volumeAplicadoService = volumeAplicadoService;
    }

    @GetMapping
    public List<VolumeAplicadoProjection> gerarRelatorio() {
        return volumeAplicadoService.gerarRelatorio();
    }
}
