package cl.bne.empleos.ms_buscar_empleos.infrastructure.controller;

import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.BuscarOfertaResponse;
import cl.bne.empleos.ms_buscar_empleos.application.usecase.BuscarOferta;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ofertas")
@RequiredArgsConstructor
public class OfertaController {

    private final BuscarOferta buscarOferta;

    @PostMapping("/buscar")
    public List<BuscarOfertaResponse> buscarOfertas(@RequestBody BuscarOfertaRequest request) {
        return buscarOferta.buscarOferta(request);
    }
}
