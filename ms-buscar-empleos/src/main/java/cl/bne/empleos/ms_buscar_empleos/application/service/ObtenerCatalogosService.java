package cl.bne.empleos.ms_buscar_empleos.application.service;

import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa.OfertaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ObtenerCatalogosService {

    private final OfertaJpaRepository repo;

    public Map<String, List<String>> execute() {
        return Map.of(
                "region", repo.findDistinctRegion(),
                "comuna", repo.findDistinctComuna(),
                "ocupacion", repo.findDistinctOcupacion(),
                "grupoEmpleo", repo.findDistinctGrupoEmpleo(),
                "nivelEducativo", repo.findDistinctNivelEducativo(),
                "jornadaLaboral", repo.findDistinctJornadaLaboral(),
                "tipoContrato", repo.findDistinctTipoContrato(),
                "origenOferta", repo.findDistinctOrigenOferta());
    }
}
