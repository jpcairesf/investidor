package com.spring.investidor.controller;

import com.spring.investidor.dto.input.InvestidorDtoInput;
import com.spring.investidor.dto.output.InvestidorDtoOutput;
import com.spring.investidor.service.InvestidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<InvestidorDtoOutput>> findAll() {
        return new ResponseEntity<List<InvestidorDtoOutput>>(investidorService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestidorDtoOutput> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<InvestidorDtoOutput>(investidorService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InvestidorDtoOutput> save(@RequestBody InvestidorDtoInput investidorDto) {
        return new ResponseEntity<InvestidorDtoOutput>(investidorService.save(investidorDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestidorDtoOutput> updateById(@RequestBody InvestidorDtoInput investidorDto, @PathVariable("id") Long id) {
        return new ResponseEntity<InvestidorDtoOutput>(investidorService.updateById(investidorDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        investidorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
