package cl.bne.empleos.ms_buscar_empleos.application.usecase;

import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaResponse;

import java.util.List;

public interface BuscarOferta {
    List<BuscarOfertaResponse> buscarOferta(BuscarOfertaRequest request);
}
