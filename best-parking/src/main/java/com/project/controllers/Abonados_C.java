package com.project.controllers;

import com.project.dto.Abonado_Dto;
import com.project.services.Abonado_S;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/abonados")
public class Abonados_C {
    @Autowired
    private Abonado_S abonado_s;
    @PostMapping
    public ResponseEntity<Abonado_Dto>createAbonado(@RequestBody Abonado_Dto abonado_dto){
        Abonado_Dto savedAbonado= abonado_s.createAbonado(abonado_dto);
        return new ResponseEntity<>(savedAbonado, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<Abonado_Dto> getAbonado(@PathVariable("id") Long id){
        Abonado_Dto abonado_dto=abonado_s.getAbonadoById(id);
        return ResponseEntity.ok(abonado_dto);
    }
    @GetMapping
    public ResponseEntity<List<Abonado_Dto>> getAllAbonados(){
        List<Abonado_Dto> abonados_Dto=abonado_s.getAllAbonados();
        return ResponseEntity.ok(abonados_Dto);
    }
    @PutMapping("{id}")
    public ResponseEntity<Abonado_Dto> updateAbonado
            (@PathVariable("id") Long id, @RequestBody Abonado_Dto updateAbonado_dto){
        Abonado_Dto abonado_dto=abonado_s.updateAbonado(id,updateAbonado_dto);
        return ResponseEntity.ok(abonado_dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deteleAbonado(@PathVariable("id") Long id){
        abonado_s.deleteAbonado(id);
        return ResponseEntity.ok("Abonado eliminado");
    }
}