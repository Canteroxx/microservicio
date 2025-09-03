package cl.bne.empleos.ms_buscar_empleos.domain.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.application.dto.SearchJobsInput;
import cl.bne.empleos.ms_buscar_empleos.application.dto.SearchJobsOutput;

import java.util.List;
import java.util.Optional;

public interface OfertaRepository {
    List<Oferta> findAll();
    Optional<Oferta> findById(Long id);
    Oferta save(Oferta oferta);
    void deleteById(Long id);
    SearchJobsOutput searchJobs(SearchJobsInput input);
}
