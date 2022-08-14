package com.spring.investidor.controller;

import com.spring.investidor.dto.input.MovimentacaoDtoInput;
import com.spring.investidor.dto.output.MovimentacaoDtoOutput;
import com.spring.investidor.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<MovimentacaoDtoOutput> aplicar(@RequestBody @Valid MovimentacaoDtoInput input) {
        return new ResponseEntity<MovimentacaoDtoOutput>(movimentacaoService.aplicar(input), HttpStatus.OK);
    }

    @PostMapping("/resgatar")
    private ResponseEntity<MovimentacaoDtoOutput> resgatar(@RequestBody @Valid MovimentacaoDtoInput input) {
        return new ResponseEntity<MovimentacaoDtoOutput>(movimentacaoService.resgatar(input), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<MovimentacaoDtoOutput>> findAll() {
        return new ResponseEntity<List<MovimentacaoDtoOutput>>(movimentacaoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/buscar")
    private ResponseEntity<List<MovimentacaoDtoOutput>> buscar(
            @RequestParam(value="investidor") String investidorCnpj,
            @RequestParam(value="ativo") String ativoNome,
            @RequestParam(value="tipo") String tipo,
            @RequestParam(value="data-inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value="data-fim")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return new ResponseEntity<List<MovimentacaoDtoOutput>>(movimentacaoService.buscar(investidorCnpj, ativoNome, tipo, dataInicio, dataFim), HttpStatus.OK);
    }
}
