package com.project.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="abonados")
public class Abonado
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="placa", nullable=false, unique=true)
    private String placa;
    @Column(name="celular", nullable=false, unique=true)
    private String celular;
    @Column(name="monto")
    private Double monto;
    @Column(name="f_ingreso")
    private Date f_ingreso;
    @Column(name="afiliado", nullable=false, unique=true)
    private Boolean Estado;
}