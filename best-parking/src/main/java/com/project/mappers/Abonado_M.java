package com.project.mappers;

import com.project.dto.Abonado_Dto;
import com.project.models.Abonado;

public class Abonado_M
{
    public static Abonado_Dto mapToAbonadoDto(Abonado abonado){
        return new Abonado_Dto(
                abonado.getId(),
                abonado.getNombre(),
                abonado.getPlaca(),
                abonado.getCelular(),
                abonado.getMonto(),
                abonado.getF_ingreso(),
                abonado.getEstado()
        );
    }
    public static Abonado mapToAbonado(Abonado_Dto abonado_dto){
        return new Abonado(
                abonado_dto.getId(),
                abonado_dto.getNombre(),
                abonado_dto.getPlaca(),
                abonado_dto.getCelular(),
                abonado_dto.getMonto(),
                abonado_dto.getF_ingreso(),
                abonado_dto.getEstado()
        );
    }
}