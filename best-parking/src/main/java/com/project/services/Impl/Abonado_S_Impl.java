package com.project.services.Impl;

import com.project.dto.Abonado_Dto;
import com.project.exceptions.ResourceNotFoundException;
import com.project.mappers.Abonado_M;
import com.project.models.Abonado;
import com.project.repositories.Abonado_R;
import com.project.services.Abonado_S;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Abonado_S_Impl implements Abonado_S
{
    private Abonado_R abonado_r;
    @Override
    public Abonado_Dto createAbonado(Abonado_Dto abonado_dto) {
        Abonado abonado= Abonado_M.mapToAbonado(abonado_dto);
        Abonado saveAbonado=abonado_r.save(abonado);
        return Abonado_M.mapToAbonadoDto(saveAbonado);
    }

    @Override
    public Abonado_Dto getAbonadoById(Long id) {
        Abonado abonado=abonado_r.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No existe abonado con id: "+id));
        return Abonado_M.mapToAbonadoDto(abonado);
    }

    @Override
    public List<Abonado_Dto> getAllAbonados() {
        List<Abonado> abonados=abonado_r.findAll();
        return abonados.stream().map((abonado)->Abonado_M.mapToAbonadoDto(abonado))
                .collect(Collectors.toList());
    }

    @Override
    public Abonado_Dto updateAbonado(Long id, Abonado_Dto updateAbonado) {
        Abonado abonado=abonado_r.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("No existe abonado con el id: "+id));
        abonado.setNombre(updateAbonado.getNombre());
        abonado.setPlaca(updateAbonado.getPlaca());
        abonado.setCelular(updateAbonado.getCelular());
        abonado.setMonto(updateAbonado.getMonto());
        abonado.setF_ingreso(updateAbonado.getF_ingreso());
        abonado.setEstado(updateAbonado.getEstado());
        return null;
    }

    @Override
    public void deleteAbonado(Long id) {
    Abonado abonado=abonado_r.findById(id).orElseThrow(
            ()->new ResourceNotFoundException("No existe el abonado con id: "+id));
    abonado_r.deleteById(id);
    }
}