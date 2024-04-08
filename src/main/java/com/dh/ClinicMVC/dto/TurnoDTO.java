package com.dh.ClinicMVC.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO {
    private Long id;
    private Long odontologo_id;
    private Long paciente_id;
    private String hora;
    private String fecha;
}
