package com.dh.ClinicMVC.service;

import com.dh.ClinicMVC.entity.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente guardar (Paciente paciente) throws Exception;

    List<Paciente> listarTodos();

    Optional<Paciente> buscarPorId(Long id);
    void actualizar(Paciente paciente) throws Exception;
    void eliminar(Long id);
    Optional<Paciente> findByDni(String dni);
}
