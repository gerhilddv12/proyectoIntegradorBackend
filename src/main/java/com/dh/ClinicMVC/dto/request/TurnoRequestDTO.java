package com.dh.ClinicMVC.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class TurnoRequestDTO {
    private Long odontologo_id;
    private Long paciente_id;
    private String fecha;
    private String hora;
}
