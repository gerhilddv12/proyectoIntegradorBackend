package com.dh.ClinicMVC.service;

import com.dh.ClinicMVC.dto.request.TurnoRequestDTO;
import com.dh.ClinicMVC.dto.response.TurnoResponseDTO;
import com.dh.ClinicMVC.dto.response.TurnoResponseDTOTable;
import com.dh.ClinicMVC.entity.Turno;
import com.dh.ClinicMVC.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDTO guardar(TurnoRequestDTO turno) throws Exception;
    List<TurnoResponseDTO> listarTodos();
    List<TurnoResponseDTOTable> listarTodosTable();

    Optional<TurnoResponseDTO> buscarPorId(Long id);

    void eliminar(Long id);

    void actualizar(Turno turno);
    Optional<List<TurnoResponseDTO>> findByOdontologoId(Long id);
    Optional<List<TurnoResponseDTO>> findByPacienteId(Long id);
}
