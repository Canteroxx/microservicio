package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.domain.model.OfertaCriterio;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.OfertaRepository;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa.OfertaJpaRepository;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.mapper.OfertaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OfertaRepositoryImpl implements OfertaRepository {

    private final OfertaJpaRepository jpaRepository;
    private final OfertaMapper ofertaMapper; 

    @Override
    public List<Oferta> findByCriterios(OfertaCriterio criterios) {
        return jpaRepository.buscarPorCriterios(
                criterios.palabraClave(),
                criterios.fechaPublicacion(),
                criterios.regiones(),
                criterios.comunas(),
                criterios.ocupaciones(),
                criterios.gruposEmpleo(),
                criterios.nivelesEducativos(),
                criterios.jornadasLaborales(),
                criterios.tiposContrato(),
                criterios.origenesOferta(),
                criterios.discapacidad()
        ).stream()
        .map(ofertaMapper::toDomain)
        .toList();
    }
}