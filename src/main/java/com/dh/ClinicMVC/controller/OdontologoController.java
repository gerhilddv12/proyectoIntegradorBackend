package com.dh.ClinicMVC.controller;

import com.dh.ClinicMVC.entity.Odontologo;
import com.dh.ClinicMVC.service.IOdontologoService;
import com.dh.ClinicMVC.service.implementation.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")

public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        // como manejar el error 500 para cuando ponemos 2 odontologos con la misma matricula
        Odontologo nuevoOdontologo = null;
        try {
            nuevoOdontologo = odontologoService.guardar(odontologo);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoOdontologo);
    }

    @GetMapping()
    public ResponseEntity<List<Odontologo>> listarTodos() {
        List<Odontologo> listaBuscados = odontologoService.listarTodos();
        return ResponseEntity.ok(listaBuscados);
    }



    @PutMapping
    public ResponseEntity<Odontologo> actualizar(@RequestBody Odontologo odontologo) throws Exception {
        ResponseEntity<Odontologo> response;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            odontologoService.actualizar(odontologo);
            response = new ResponseEntity(odontologo, HttpStatus.OK);
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Long id) {
       Optional<Odontologo> odontologo = odontologoService.buscarPorId(id);
       return odontologo != null ? ResponseEntity.ok(odontologo.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        ResponseEntity<String> response;
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminar(id);
            response = ResponseEntity.ok("Se elimino el odontologo con id " + id);
        }
        else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @GetMapping("/matricula/{matricula}")
    public  ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable("matricula") String matricula) {
        Optional<Odontologo> odontologoOptional = odontologoService.findByMatricula(matricula);
        if (odontologoOptional.isPresent()) {
            return ResponseEntity.ok(odontologoOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/apellido/{apellido}")
    public  ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable("apellido") String apellido) {
        Optional<List<Odontologo>> listaBuscados = odontologoService.findByApellido(apellido);
        if (!listaBuscados.isEmpty()) {
            return ResponseEntity.ok(listaBuscados.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public  ResponseEntity<List<Odontologo>> buscarPorNombre(@PathVariable("nombre") String nombre) {
        Optional<List<Odontologo>> listaBuscados = odontologoService.findByNombre(nombre);
        if (!listaBuscados.isEmpty()) {
            return ResponseEntity.ok(listaBuscados.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


