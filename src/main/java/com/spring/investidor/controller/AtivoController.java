package com.spring.investidor.controller;

import com.spring.investidor.dto.input.AtivoDtoInput;
import com.spring.investidor.dto.output.AtivoDtoOutput;
import com.spring.investidor.service.AtivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ativo")
public class AtivoController {

    private AtivoService ativoService;

    @Autowired
    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @GetMapping
    public ResponseEntity<List<AtivoDtoOutput>> findAll() {
        return new ResponseEntity<List<AtivoDtoOutput>>(ativoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoDtoOutput> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<AtivoDtoOutput>(ativoService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AtivoDtoOutput> save(@RequestBody @Valid AtivoDtoInput ativoDtoInput) {
        return new ResponseEntity<AtivoDtoOutput>(ativoService.save(ativoDtoInput), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtivoDtoOutput> updateById(@RequestBody @Valid AtivoDtoInput ativoDtoInput, @PathVariable("id") Long id) {
        return new ResponseEntity<AtivoDtoOutput>(ativoService.updateById(ativoDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        ativoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
