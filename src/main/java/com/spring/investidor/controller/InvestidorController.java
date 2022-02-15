package com.spring.investidor.controller;

import com.spring.investidor.dto.input.InvestidorDtoInput;
import com.spring.investidor.dto.output.InvestidorDtoOutput;
import com.spring.investidor.service.InvestidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investidor")
public class InvestidorController {


    private InvestidorService investidorService;

    @Autowired
    public InvestidorController(InvestidorService investidorService) {
        this.investidorService = investidorService;
    }

    @GetMapping
    public List<InvestidorDtoOutput> findAll() {
        return investidorService.findAll();
    }

    @GetMapping("/{id}")
    public InvestidorDtoOutput findById(@PathVariable("id") Long id) {
        return investidorService.findById(id);
    }

    @PostMapping
    public InvestidorDtoOutput save(@RequestBody InvestidorDtoInput investidorDto) {
        return investidorService.save(investidorDto);
    }

    @PutMapping("/{id}")
    public InvestidorDtoOutput updateById(@RequestBody InvestidorDtoInput investidorDto, @PathVariable("id") Long id) {
        return investidorService.updateById(investidorDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        investidorService.deleteById(id);
    }
}
