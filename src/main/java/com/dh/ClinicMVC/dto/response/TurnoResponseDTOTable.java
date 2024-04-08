package com.dh.ClinicMVC.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDTOTable {
    private Long id;
    private String hora;
    private String fecha;
    private Long odontologo_id;
    private String nombreOdontologo;
    private String apellidoOdontologo;
    private String matriculaOdontologo;
    private Long paciente_id;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String dniPaciente;
}
