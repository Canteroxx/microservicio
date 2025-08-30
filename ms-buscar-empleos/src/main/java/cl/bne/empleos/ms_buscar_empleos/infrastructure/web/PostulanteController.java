package cl.bne.empleos.ms_buscar_empleos.infrastructure.web;

import cl.bne.empleos.ms_buscar_empleos.application.PostulanteService;
import cl.bne.empleos.ms_buscar_empleos.domain.model.Postulante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/postulantes")
public class PostulanteController {

    private final PostulanteService service;

    public PostulanteController(PostulanteService service) {
        this.service = service;
    }

    // Crear postulante
    @PostMapping
    public ResponseEntity<Postulante> crear(@RequestBody Postulante postulante) {
        Postulante creado = service.registrar(postulante);
        return ResponseEntity.created(URI.create("/api/postulantes/" + creado.getId()))
                .body(creado);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Postulante>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Postulante> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Postulante> actualizar(@PathVariable Long id, @RequestBody Postulante postulante) {
        return service.actualizar(id, postulante)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = service.eliminar(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
