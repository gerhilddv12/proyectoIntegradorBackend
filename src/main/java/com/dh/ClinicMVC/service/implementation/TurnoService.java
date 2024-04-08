package com.dh.ClinicMVC.service.implementation;

import com.dh.ClinicMVC.dto.request.TurnoRequestDTO;
import com.dh.ClinicMVC.dto.response.TurnoResponseDTO;
import com.dh.ClinicMVC.dto.response.TurnoResponseDTOTable;
import com.dh.ClinicMVC.entity.Odontologo;
import com.dh.ClinicMVC.entity.Paciente;
import com.dh.ClinicMVC.entity.Turno;

import com.dh.ClinicMVC.exception.BadRequest;
import com.dh.ClinicMVC.exception.ResourceNotFoundException;
import com.dh.ClinicMVC.repository.ITurnosRepository;
import com.dh.ClinicMVC.service.IOdontologoService;
import com.dh.ClinicMVC.service.IPacienteService;
import com.dh.ClinicMVC.service.ITurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private ITurnosRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(TurnoService.class);

    @Autowired
    public TurnoService(ITurnosRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }


    @Override
    public TurnoResponseDTO guardar(TurnoRequestDTO turnoRequestDTO) throws BadRequest {
        LOGGER.info("Persitiendo un Turno");
        //convertir el String del turnoRequestDTO a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        //creamos el LocalDate que vamos a tener que persistir en la BD
        LocalDate date = LocalDate.parse(turnoRequestDTO.getFecha(), formatter);
        LocalTime time = LocalTime.parse(turnoRequestDTO.getHora(),formatterTime);

        // chequeamos que el turno no tenga el mismo odontologo en la misma fecha y hora que un turno ya guardado
        Optional<List<TurnoResponseDTO>> turnosChequeoOdontologo = findByOdontologoId(turnoRequestDTO.getOdontologo_id());
        if (turnosChequeoOdontologo.isPresent()) {
            List<TurnoResponseDTO> turnosOdontologo = turnosChequeoOdontologo.get();

            for (TurnoResponseDTO turnoExistente : turnosOdontologo) {
                LocalDate fechaTurnoExistente = LocalDate.parse(turnoExistente.getFecha(), formatter);
                LocalTime horaTurnoExistente = LocalTime.parse(turnoExistente.getHora(), formatterTime);
                if (fechaTurnoExistente.equals(date) && horaTurnoExistente.equals(time)) {
                    LOGGER.error("Este odontologo ya tiene un turno asignado en esta fecha y hora");
                    throw new BadRequest("Este odontologo ya tiene un turno asignado en esta fecha y hora");
                }
            }
        }

        //mapear el dto que recibimos a una entidad
        //instanciar turnoEntity -> para persitirlo en la BD
        Turno turnoEntity = new Turno(); //Turno(null, null, null, null)

        //instanciamos Paciente
        Paciente paciente = new Paciente();
        paciente.setId(turnoRequestDTO.getPaciente_id());

        //instanciamos Odontologo
        Odontologo odontologo = new Odontologo();
        odontologo.setId(turnoRequestDTO.getOdontologo_id());

        //seteamos Paciente y Odontologo a la entidad Turno
        turnoEntity.setOdontologo(odontologo);
        turnoEntity.setPaciente(paciente);

        //seteamos al turno la fecha
        turnoEntity.setFecha(date);
        turnoEntity.setHora(time);

        //persistir el turno en la BD
        turnoRepository.save(turnoEntity);
        //acá ya tenemos la entidad con id
        LOGGER.info("Turno Persistido");
        //ESTE ES EL CAMINO DE VUELTA HACIA EL CONTROLADOR
        //PORQUE YA SE PERSISTIÓ LA ENTIDAD
        //mapear la entidad persistida en un dto
        TurnoResponseDTO turnoResponseDTO = new TurnoResponseDTO(); //TurnoDto(null, null, null, null)
        turnoResponseDTO.setId(turnoEntity.getId());
        turnoResponseDTO.setHora(turnoEntity.getHora().toString());
        turnoResponseDTO.setOdontologo_id(turnoEntity.getOdontologo().getId());
        turnoResponseDTO.setPaciente_id(turnoEntity.getPaciente().getId());
        turnoResponseDTO.setFecha(turnoEntity.getFecha().toString());

        return turnoResponseDTO;
    }

    @Override
    public List<TurnoResponseDTO> listarTodos() {
        LOGGER.info("Buscando lista de turnos");
        // creamos la lista donde vamos a guardar los TurnosResponse
        List<TurnoResponseDTO> turnoResponseDTOList = new ArrayList<>();
        //creamos la lista de Turnos y traemos de la base de datos los turnos
       List<Turno> turnoList = turnoRepository.findAll();
            // mapeamos por cada turnos en la listaTurnos a TurnoResponseDTO
        for (Turno turno:turnoList) {
            TurnoResponseDTO turnoResponseDTO = new TurnoResponseDTO();
            turnoResponseDTO.setId(turno.getId());
            turnoResponseDTO.setFecha(turno.getFecha().toString());
            turnoResponseDTO.setHora(turno.getHora().toString());
            turnoResponseDTO.setPaciente_id(turno.getPaciente().getId());
            turnoResponseDTO.setOdontologo_id(turno.getOdontologo().getId());
            turnoResponseDTOList.add(turnoResponseDTO);
        }
        return turnoResponseDTOList;
    }

    @Override
    public Optional<TurnoResponseDTO> buscarPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando el turno con id: "+id );
        // traemos de la base de datos el turno
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        // si el turno se encuentra lo procesamos
        if (turnoOptional.isPresent()) {
            TurnoResponseDTO turnoResponseDTO = new TurnoResponseDTO();
            Turno turno = turnoOptional.get();
            turnoResponseDTO.setId(turno.getId());
            turnoResponseDTO.setFecha(turno.getFecha().toString());
            turnoResponseDTO.setHora(turno.getHora().toString());
            turnoResponseDTO.setPaciente_id(turno.getPaciente().getId());
            turnoResponseDTO.setOdontologo_id(turno.getOdontologo().getId());
            return Optional.of(turnoResponseDTO);
        } else {
            throw  new ResourceNotFoundException("No se encontro el Turno con id: " +id);
        }

    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if (turnoOptional.isPresent()) {
            turnoRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("El turno con id: " + id +" no se puede eliminar porque no existe");
        }
    }

    @Override
    public void actualizar(Turno turno) {
        Optional<Turno> turnoOptional = turnoRepository.findById(turno.getId());
        if (turnoOptional.isPresent()) {
            turnoRepository.save(turno);
        }
            else  {
            throw new ResourceNotFoundException("El turno con id: " + turno.getId() +" no se puede actualizar porque no existe");
        }
    }

    @Override
    public Optional<List<TurnoResponseDTO>> findByOdontologoId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando los turnos con Odontologo id: "+id);
        Optional<List<Turno>> turnoOptionalList = turnoRepository.findByOdontologoId(id);
        List<TurnoResponseDTO> turnoResponseDTOList = new ArrayList<>();
        if (turnoOptionalList.isPresent()) {
            for (Turno turno:turnoOptionalList.get()) {
                TurnoResponseDTO turnoResponseDTO = new TurnoResponseDTO();
                turnoResponseDTO.setId(turno.getId());
                turnoResponseDTO.setFecha(turno.getFecha().toString());
                turnoResponseDTO.setHora(turno.getHora().toString());
                turnoResponseDTO.setPaciente_id(turno.getPaciente().getId());
                turnoResponseDTO.setOdontologo_id(turno.getOdontologo().getId());
                turnoResponseDTOList.add(turnoResponseDTO);
                return Optional.of(turnoResponseDTOList);
            }
        }
       return Optional.empty();
    }

    @Override
    public Optional<List<TurnoResponseDTO>> findByPacienteId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando los turnos con Paciente id: "+id);
        Optional<List<Turno>> turnoOptionalList = turnoRepository.findByPacienteId(id);
        List<TurnoResponseDTO> turnoResponseDTOList = new ArrayList<>();
        if (turnoOptionalList.isPresent()) {
            for (Turno turno:turnoOptionalList.get()) {
                TurnoResponseDTO turnoResponseDTO = new TurnoResponseDTO();
                turnoResponseDTO.setId(turno.getId());
                turnoResponseDTO.setFecha(turno.getFecha().toString());
                turnoResponseDTO.setHora(turno.getHora().toString());
                turnoResponseDTO.setPaciente_id(turno.getPaciente().getId());
                turnoResponseDTO.setOdontologo_id(turno.getOdontologo().getId());
                turnoResponseDTOList.add(turnoResponseDTO);
                return Optional.of(turnoResponseDTOList);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<TurnoResponseDTOTable> listarTodosTable() {
        LOGGER.info("Buscando lista de turnos completa");
        // inicializamos las variables odontologo y paciente
            Odontologo odontologo = null;
            Paciente paciente = null;

        // creamos la lista donde vamos a guardar los TurnosResponse
        List<TurnoResponseDTOTable> turnoResponseDTOList = new ArrayList<>();

        //creamos la lista de Turnos y traemos de la base de datos los turnos
        List<Turno> turnoList = turnoRepository.findAll();

        // mapeamos por cada turnos en la listaTurnos a TurnoResponseDTO
        for (Turno turno:turnoList) {
            TurnoResponseDTOTable turnoResponseDTOTable = new TurnoResponseDTOTable();
            turnoResponseDTOTable.setId(turno.getId());
            turnoResponseDTOTable.setFecha(turno.getFecha().toString());
            turnoResponseDTOTable.setHora(turno.getHora().toString());
            System.out.println(turno.getPaciente().getId());

            // mapeando el paciente dentro del response
            paciente = turno.getPaciente();

            turnoResponseDTOTable.setPaciente_id(paciente.getId());
            turnoResponseDTOTable.setNombrePaciente(paciente.getNombre());
            turnoResponseDTOTable.setApellidoPaciente(paciente.getApellido());
            turnoResponseDTOTable.setDniPaciente(paciente.getDni());

            // mapeando el odontologo dentro del response
            odontologo = turno.getOdontologo();

            turnoResponseDTOTable.setOdontologo_id(odontologo.getId());
            turnoResponseDTOTable.setNombreOdontologo(odontologo.getNombre());
            turnoResponseDTOTable.setApellidoOdontologo(odontologo.getApellido());
            turnoResponseDTOTable.setMatriculaOdontologo(odontologo.getMatricula());
            turnoResponseDTOList.add(turnoResponseDTOTable);
        }
        return turnoResponseDTOList;
    }
}
