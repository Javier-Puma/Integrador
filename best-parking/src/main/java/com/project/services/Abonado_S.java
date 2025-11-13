package com.project.services;

import com.project.dto.Abonado_Dto;
import java.util.List;

public interface Abonado_S
{
    Abonado_Dto createAbonado(Abonado_Dto abonado_dto);
    Abonado_Dto getAbonadoById(Long id);
    List<Abonado_Dto> getAllAbonados();
    Abonado_Dto updateAbonado(Long id, Abonado_Dto updateAbonado);
    void deleteAbonado(Long id);
}