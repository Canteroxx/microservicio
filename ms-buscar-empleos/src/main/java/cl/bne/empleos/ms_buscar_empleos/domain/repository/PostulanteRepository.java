package cl.bne.empleos.ms_buscar_empleos.domain.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Postulante;

import java.util.List;
import java.util.Optional;

public interface PostulanteRepository {
    Postulante save(Postulante postulante);
    Optional<Postulante> findById(Long id);
    List<Postulante> findAll();
    void deleteById(Long id);
}
