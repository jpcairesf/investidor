package com.spring.investidor.controller;

import com.spring.investidor.dto.input.AtivoDtoInput;
import com.spring.investidor.dto.output.AtivoDtoOutput;
import com.spring.investidor.service.AtivoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<AtivoDtoOutput> findAll() { return ativoService.findAll(); }

    @GetMapping("/{id}")
    public AtivoDtoOutput findById(@PathVariable("id") Long id) { return ativoService.findById(id); }

    @PostMapping
    public AtivoDtoOutput save(@RequestBody @Valid AtivoDtoInput ativoDtoInput) { return ativoService.save(ativoDtoInput); }

    @PutMapping("/{id}")
    public AtivoDtoOutput updateById(@RequestBody @Valid AtivoDtoInput ativoDtoInput, @PathVariable("id") Long id) { return ativoService.updateById(ativoDtoInput, id); }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) { ativoService.deleteById(id); }
}
