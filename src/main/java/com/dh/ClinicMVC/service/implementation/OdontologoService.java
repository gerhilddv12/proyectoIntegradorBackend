package com.dh.ClinicMVC.service.implementation;

import com.dh.ClinicMVC.entity.Odontologo;
import com.dh.ClinicMVC.exception.BadRequest;
import com.dh.ClinicMVC.exception.ResourceNotFoundException;
import com.dh.ClinicMVC.repository.IOdontologoRepository;
import com.dh.ClinicMVC.service.IOdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private IOdontologoRepository odontologoRepository;
    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Autowired
    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo guardar(Odontologo odontologo) throws BadRequest {
        LOGGER.info("Persistiendo un Odontólogo");
        Optional<Odontologo> odontologoOptional = odontologoRepository.findByMatricula(odontologo.getMatricula());
        if (odontologoOptional.isEmpty()) {
            // Si la matrícula no existe, verifica campos vacíos y guardar el odontólogo
            if (odontologo.getNombre() == null || odontologo.getApellido() == null || odontologo.getMatricula() == null
                    || odontologo.getNombre().trim().isEmpty() || odontologo.getApellido().trim().isEmpty() || odontologo.getMatricula().trim().isEmpty()) {
                throw new BadRequest("No puedes poner campos vacíos");
            }
            LOGGER.info("Guardado exitosamente");
            return odontologoRepository.save(odontologo);
        } else {
            LOGGER.error("Un Odontólogo con la misma Matrícula ya se encontraba en la base de datos");
            // La matrícula ya existe en la base de datos
            throw new BadRequest("Un Odontólogo con la misma Matrícula ya se encontraba en la base de datos");
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        LOGGER.info("Buscando la lista de Odontólogos");
        return odontologoRepository.findAll();
    }

    @Override
    public Optional<Odontologo> buscarPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando el Odontólogo con id: " + id);
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if (odontologoOptional.isPresent()) {
            return odontologoOptional;
        } else {
            LOGGER.error("No se encontró al Odontólogo con ID: " + id);
            throw new ResourceNotFoundException("No se encontró al Odontólogo");
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) throws BadRequest {
        LOGGER.info("Actualizando el Odontólogo con id: " + odontologo.getId());
        Optional<Odontologo> odontologoOptional = odontologoRepository.findByMatricula(odontologo.getMatricula());
        if ((odontologoOptional.isPresent() && odontologo.getId().equals(odontologoOptional.get().getId())) || odontologoOptional.isEmpty()) {
            // Si la matrícula no existe, verifica campos vacíos y guardar el odontólogo
            if (odontologo.getNombre() == null || odontologo.getApellido() == null || odontologo.getMatricula() == null
                    || odontologo.getNombre().trim().isEmpty() || odontologo.getApellido().trim().isEmpty() || odontologo.getMatricula().trim().isEmpty()) {
                LOGGER.error("Al menos uno de los campos a actualizar está vacío");
                throw new BadRequest("No puedes poner campos vacíos");
            }
            LOGGER.info("Actualizado exitosamente");
            odontologoRepository.save(odontologo);
        } else {
            LOGGER.error("Ya existe un Odontólogo con esta Matrícula en la base de datos");
            // La matrícula ya existe en la base de datos
            throw new BadRequest("La matrícula del Odontólogo ya existe");
        }
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {
        LOGGER.info("Eliminando al Odontólogo con id: " + id);
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if (odontologoOptional.isPresent()) {
            LOGGER.info("Odontólogo eliminado exitosamente");
            odontologoRepository.deleteById(id);
        } else {
            LOGGER.error("El Odontólogo con ID: " + id + " no se encontró en la base de datos");
            throw new ResourceNotFoundException("El Odontólogo con id: " + id + " no se encontró en la base de datos");
        }
    }

    @Override
    public Optional<List<Odontologo>> findByNombre(String nombre) throws ResourceNotFoundException {
        LOGGER.info("Buscando Odontólogos por nombre: " + nombre);
        Optional<List<Odontologo>> odontologosOptional = odontologoRepository.findByNombre(nombre);
        if (odontologosOptional.isPresent()) {
            return odontologosOptional;
        } else {
            LOGGER.error("No se encontraron Odontólogos con nombre: " + nombre);
            throw new ResourceNotFoundException("Odontólogos no encontrados con nombre: " + nombre);
        }
    }

    @Override
    public Optional<List<Odontologo>> findByApellido(String apellido) throws ResourceNotFoundException {
        LOGGER.info("Buscando Odontólogos por apellido: " + apellido);
        Optional<List<Odontologo>> odontologosOptional = odontologoRepository.findByApellido(apellido);
        if (odontologosOptional.isPresent()) {
            return odontologosOptional;
        } else {
            LOGGER.error("No se encontraron Odontólogos con apellido: " + apellido);
            throw new ResourceNotFoundException("Odontólogos no encontrados con apellido: " + apellido);
        }
    }

    @Override
    public Optional<Odontologo> findByMatricula(String matricula) throws ResourceNotFoundException {
        LOGGER.info("Buscando Odontólogo por matrícula: " + matricula);
        Optional<Odontologo> odontologoOptional = odontologoRepository.findByMatricula(matricula);
        if (odontologoOptional.isPresent()) {
            return odontologoOptional;
        } else {
            LOGGER.error("No se encontró al Odontólogo con matrícula: " + matricula);
            throw new ResourceNotFoundException("Odontólogo no encontrado con matrícula: " + matricula);
        }
    }
}