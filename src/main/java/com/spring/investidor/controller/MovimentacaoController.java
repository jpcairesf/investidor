package com.spring.investidor.controller;

import com.spring.investidor.dto.input.MovimentacaoDtoInput;
import com.spring.investidor.dto.output.MovimentacaoDtoOutput;
import com.spring.investidor.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    private MovimentacaoService movimentacaoService;

    @Autowired
    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/aplicar")
    private MovimentacaoDtoOutput aplicar(@RequestBody @Valid MovimentacaoDtoInput input) {
        return movimentacaoService.aplicar(input);
    }

    @PostMapping("/resgatar")
    private MovimentacaoDtoOutput resgatar(@RequestBody @Valid MovimentacaoDtoInput input) {
        return movimentacaoService.resgatar(input);
    }

    @GetMapping
    private List<MovimentacaoDtoOutput> findAll() { return movimentacaoService.findAll(); }

    @GetMapping("/buscar")
    private List<MovimentacaoDtoOutput> buscar(
            @RequestParam(value="investidor") String investidorCnpj,
            @RequestParam(value="ativo") String ativoNome,
            @RequestParam(value="tipo") String tipo,
            @RequestParam(value="data-inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value="data-fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return movimentacaoService.buscar(investidorCnpj, ativoNome, tipo, dataInicio, dataFim);
    }
}
