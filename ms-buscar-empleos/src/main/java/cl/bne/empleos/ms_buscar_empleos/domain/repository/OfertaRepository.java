package cl.bne.empleos.ms_buscar_empleos.domain.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.domain.model.OfertaCriterio;

import java.util.List;
public interface OfertaRepository {
    List<Oferta> findByCriterios(OfertaCriterio criterios);
}

