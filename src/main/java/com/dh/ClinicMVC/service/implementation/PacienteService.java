package com.dh.ClinicMVC.service.implementation;


import com.dh.ClinicMVC.entity.Odontologo;
import com.dh.ClinicMVC.entity.Paciente;
import com.dh.ClinicMVC.exception.BadRequest;
import com.dh.ClinicMVC.exception.ResourceNotFoundException;
import com.dh.ClinicMVC.repository.IPacienteRepository;
import com.dh.ClinicMVC.service.IPacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private static IPacienteRepository pacienteRepository;
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(PacienteService.class);
    @Autowired
    private PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardar(Paciente paciente) throws BadRequest {
        LOGGER.info("Persistiendo un Paciente");
        Optional<Paciente> odontologoOptional = pacienteRepository.findByDni(paciente.getDni());
        if (odontologoOptional.isEmpty()) {
            // si el dni no existe, verifica campos vacíos y guardar el odontólogo
            if (paciente.getNombre() == null || paciente.getApellido() == null || paciente.getDni() == null
                    || paciente.getNombre().trim().isEmpty() || paciente.getApellido().trim().isEmpty() || paciente.getDni().trim().isEmpty()) {
                throw new BadRequest("No puedes poner campos vacíos");
            }
            LOGGER.info("Guardado exitosamente");
            return pacienteRepository.save(paciente);
        } else {
            LOGGER.error("Un paciente con el mismo Dni ya se encontraba en la base de datos");
            // La matrícula ya existe en la base de datos
            throw new BadRequest("Un paciente con el mismo Dni ya se encontraba en la base de datos");
        }
    }

    @Override
    public List<Paciente> listarTodos() {
        LOGGER.info("Buscando la lista de pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando el paciente con id: " + id);
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            return pacienteOptional;
        }
        else  {
            throw new ResourceNotFoundException("No se encontro al paciente");
        }
    }

    @Override
    public void actualizar(Paciente paciente) throws BadRequest {
        LOGGER.info("actualizando el paciente con id: " + paciente.getId());
        Optional<Paciente> pacienteOptional = pacienteRepository.findByDni(paciente.getDni());
        if ((pacienteOptional.isPresent() && pacienteOptional.get().getId().equals(paciente.getId())) || pacienteOptional.isEmpty()) {
            // si el dni no existe, verifica campos vacíos y guardar el odontólogo
            if (paciente.getNombre() == null || paciente.getApellido() == null || paciente.getDni() == null
                    || paciente.getNombre().trim().isEmpty() || paciente.getApellido().trim().isEmpty() || paciente.getDni().trim().isEmpty()) {
                LOGGER.error("Al menos uno de los campos a actualizar esta vacio");
                throw new BadRequest("No puedes poner campos vacíos");
            }
            LOGGER.info("Actualizado exitosamente");
            pacienteRepository.save(paciente);
        } else {
            // La matrícula ya existe en la base de datos
            LOGGER.error("Ya existe un paciente con este dni en la base de datos");
            throw new BadRequest("El dni del paciente ya existe");
        }
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando al paciente con id: " + id);
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (pacienteOptional.isPresent()) {
            LOGGER.info("Paciente eliminado exitosamente");
            pacienteRepository.deleteById(id);

        } else {
            throw new ResourceNotFoundException("El Paciente con id: " + id +" no se encontro en la base de datos");
        }
    }

    @Override
    public Optional<Paciente> findByDni(String dni) throws ResourceNotFoundException {
        LOGGER.info("Buscando al paciente con dni: " +dni);
        Optional<Paciente> pacienteOptional = pacienteRepository.findByDni(dni);
        if (pacienteOptional.isPresent()) {
            return pacienteOptional;
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}