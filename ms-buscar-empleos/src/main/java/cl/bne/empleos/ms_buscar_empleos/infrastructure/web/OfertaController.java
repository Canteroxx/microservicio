package cl.bne.empleos.ms_buscar_empleos.infrastructure.web;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private List<Oferta> ofertas = new ArrayList<>();

    public OfertaController() {
        // Mock de datos (puedes reemplazarlo por repository luego)
        ofertas.add(new Oferta(1L, "Desarrollador Java", "Backend con Spring Boot", "Empresa A", "ABIERTA"));
        ofertas.add(new Oferta(2L, "Analista QA", "Pruebas automatizadas", "Empresa B", "ABIERTA"));
        ofertas.add(new Oferta(3L, "Data Scientist", "Modelos predictivos", "Empresa C", "CERRADA"));
    }

    @GetMapping
    public List<Oferta> listar() {
        return ofertas;
    }
}
