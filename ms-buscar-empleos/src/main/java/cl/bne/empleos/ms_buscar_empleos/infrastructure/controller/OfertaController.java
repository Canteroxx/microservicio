package cl.bne.empleos.ms_buscar_empleos.infrastructure.controller;

import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaResponse;
import cl.bne.empleos.ms_buscar_empleos.application.service.ObtenerCatalogosService;
import cl.bne.empleos.ms_buscar_empleos.application.usecase.BuscarOferta;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ofertas")
@RequiredArgsConstructor
public class OfertaController {

    private final BuscarOferta buscarOferta;
    private final ObtenerCatalogosService obtenerCatalogosService; // <-- NUEVO

    @PostMapping("/buscar")
    public List<BuscarOfertaResponse> buscarOfertas(@RequestBody BuscarOfertaRequest request) {
        return buscarOferta.buscarOferta(request);
    }

    @GetMapping("/catalogos")
    public Map<String, List<String>> catalogos() {
        return obtenerCatalogosService.execute();
    }
}
