package com.spring.investidor.controller;

import com.spring.investidor.relatorio.ExtratoDto;
import com.spring.investidor.service.ExtratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/extrato")
public class ExtratoController {

    private ExtratoService extratoService;

    @Autowired
    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService;
    }

    @GetMapping
    public ResponseEntity<ExtratoDto> extrato(
            @RequestParam(value="investidor") String investidor,
            @RequestParam(value="ativo", required = false) String ativo,
            @RequestParam(value="tipo", required = false) String tipo,
            @RequestParam(value="data-inicio")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value="periodo", required = false, defaultValue = "30") Integer periodo) {
        return new ResponseEntity<ExtratoDto>(extratoService.extrato(investidor, ativo, tipo, dataInicio, periodo), HttpStatus.OK);
    }
}
