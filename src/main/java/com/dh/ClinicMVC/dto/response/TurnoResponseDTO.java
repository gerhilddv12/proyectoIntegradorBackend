package com.dh.ClinicMVC.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDTO {
    private Long id;
    private Long odontologo_id;
    private Long paciente_id;
    private String hora;
    private String fecha;
}