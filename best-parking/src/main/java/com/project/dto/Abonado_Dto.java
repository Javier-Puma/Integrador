package com.project.dto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Abonado_Dto
{
    private Long id;
    private String nombre;
    private String placa;
    private String celular;
    private Double monto;
    private Date f_ingreso;
    private Boolean Estado;
}