package cl.bne.empleos.ms_buscar_empleos.application.service;

import cl.bne.empleos.ms_buscar_empleos.application.usecase.BuscarOferta;
import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaResponse;
import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.domain.model.OfertaCriterio;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.OfertaRepository;
import cl.bne.empleos.ms_buscar_empleos.application.mapper.BuscarOfertaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarOfertaService implements BuscarOferta{
    private final OfertaRepository ofertaRepository;
    private final BuscarOfertaMapper mapper;

    @Override
    public List<BuscarOfertaResponse> buscarOferta(BuscarOfertaRequest request) {
        OfertaCriterio criterios = mapper.toCriterio(request);

        List<Oferta> ofertas = ofertaRepository.findByCriterios(criterios);

        return ofertas.stream()
                .map(mapper::toResponse)
                .toList();
    }
}
