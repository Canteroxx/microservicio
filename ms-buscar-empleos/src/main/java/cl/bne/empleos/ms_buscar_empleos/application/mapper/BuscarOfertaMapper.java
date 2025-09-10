package cl.bne.empleos.ms_buscar_empleos.application.mapper;

import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaResponse;
import cl.bne.empleos.ms_buscar_empleos.domain.model.OfertaCriterio;
import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuscarOfertaMapper{

    OfertaCriterio toCriterio(BuscarOfertaRequest request);
    BuscarOfertaResponse toResponse(Oferta oferta);
}
