package com.dh.ClinicMVC.controller;

import com.dh.ClinicMVC.dto.TurnoDTO;
import com.dh.ClinicMVC.dto.request.TurnoRequestDTO;
import com.dh.ClinicMVC.dto.response.TurnoResponseDTO;
import com.dh.ClinicMVC.dto.response.TurnoResponseDTOTable;
import com.dh.ClinicMVC.entity.Paciente;
import com.dh.ClinicMVC.entity.Turno;
import com.dh.ClinicMVC.service.IOdontologoService;
import com.dh.ClinicMVC.service.IPacienteService;
import com.dh.ClinicMVC.service.ITurnoService;
import com.dh.ClinicMVC.service.implementation.OdontologoService;
import com.dh.ClinicMVC.service.implementation.PacienteService;
import com.dh.ClinicMVC.service.implementation.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private static final Logger LOGGER = Logger.getLogger(TurnoController.class);

    private ITurnoService turnoService;
    private IOdontologoService odontologoService;
    private IPacienteService pacienteService;

    @Autowired
    public TurnoController(TurnoService turnoService, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoService = turnoService;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
    }


    //endpoint para guardar un turno
    @PostMapping
    public ResponseEntity<TurnoResponseDTO> guardar(@RequestBody TurnoRequestDTO turnoRequestDTO) throws Exception {
        ResponseEntity<TurnoResponseDTO> response;

        LOGGER.info("esto trae el turnoRequestDTO: " + turnoRequestDTO);
//        vamos a chequear que exista el odontologo y la paciente
        if (odontologoService.buscarPorId(turnoRequestDTO.getOdontologo_id()) != null &&
                pacienteService.buscarPorId(turnoRequestDTO.getPaciente_id()) != null) {

            //setear una respuesta en 200 y vamos a hacer que devuelva el turnoRequestDTO
            response = ResponseEntity.ok(turnoService.guardar(turnoRequestDTO));

        } else {
            //si no existe el odontologo o el paciente
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        return response;
    }
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<TurnoResponseDTO> turnoResponseDTOOptional = turnoService.buscarPorId(id);
        if (turnoResponseDTOOptional.isPresent()){
            return ResponseEntity.ok(turnoResponseDTOOptional.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        ResponseEntity<String> response;
        Optional<TurnoResponseDTO> turnoBuscado = turnoService.buscarPorId(id);
        if (turnoBuscado.isPresent()) {
            turnoService.eliminar(id);
            response = ResponseEntity.ok("Se eliminó el turno con id " + id);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }
    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> listarTodos() {
        List<TurnoResponseDTO> listaTurnos = turnoService.listarTodos();
        if (!listaTurnos.isEmpty()) {
            return ResponseEntity.ok(listaTurnos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/table")
    public ResponseEntity<List<TurnoResponseDTOTable>> listarTodosTable() {
        List<TurnoResponseDTOTable> listaTurnos = turnoService.listarTodosTable();
        if (!listaTurnos.isEmpty()) {
            return ResponseEntity.ok(listaTurnos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<TurnoResponseDTO>> buscarPorPacienteId(@RequestParam Long id) {
        Optional<List<TurnoResponseDTO>> optionalTurnoResponseDTOList = turnoService.findByPacienteId(id);
        if (optionalTurnoResponseDTOList.isPresent()) {
            List<TurnoResponseDTO> turnoResponseDTOList = optionalTurnoResponseDTOList.get();
            return ResponseEntity.ok(turnoResponseDTOList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/odontolgo/{id}")
    public ResponseEntity<List<TurnoResponseDTO>> buscarPorOdontologoId(@RequestParam Long id) {
        Optional<List<TurnoResponseDTO>> optionalTurnoResponseDTOList = turnoService.findByPacienteId(id);
        if (optionalTurnoResponseDTOList.isPresent()) {
            List<TurnoResponseDTO> turnoResponseDTOList = optionalTurnoResponseDTOList.get();
            return ResponseEntity.ok(turnoResponseDTOList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
