package com.dh.ClinicMVC.repository;

import com.dh.ClinicMVC.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITurnosRepository extends JpaRepository<Turno, Long> {
    @Query("SELECT t FROM Turno t WHERE t.odontologo.id = ?1")
    Optional<List<Turno>> findByOdontologoId(Long id);

    @Query("SELECT t FROM Turno t WHERE t.paciente.id = ?1")
    Optional<List<Turno>> findByPacienteId(Long id);


}
