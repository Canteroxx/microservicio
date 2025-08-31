package cl.bne.empleos.ms_buscar_empleos.infrastructure.web;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.web.dto.CreateOfertaRequest;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.web.dto.UpdateOfertaRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final List<Oferta> ofertas = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);

    public OfertaController() {
        ofertas.add(new Oferta(1L, "Desarrollador Java", "Backend con Spring Boot", "Empresa A", "ABIERTA"));
        ofertas.add(new Oferta(2L, "Analista QA", "Pruebas automatizadas", "Empresa B", "ABIERTA"));
        ofertas.add(new Oferta(3L, "Data Scientist", "Modelos predictivos", "Empresa C", "CERRADA"));
        seq.set(3);
    }

    @GetMapping
    public List<Oferta> listar() {
        return ofertas;
    }

  
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "") String estado,
            @RequestParam(defaultValue = "") String empresa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titulo,asc") String sort
    ) {
        final String qNorm = q.trim().toLowerCase();
        final String estadoNorm = estado.trim().toUpperCase();
        final String empresaNorm = empresa.trim().toLowerCase();

        List<Oferta> filtradas = ofertas.stream()
                .filter(o ->
                        qNorm.isEmpty()
                                || o.getTitulo().toLowerCase().contains(qNorm)
                                || o.getDescripcion().toLowerCase().contains(qNorm)
                                || o.getEmpresa().toLowerCase().contains(qNorm)
                )
                .filter(o -> estadoNorm.isEmpty() || estadoNorm.equals(o.getEstado()))
                .filter(o -> empresaNorm.isEmpty() || o.getEmpresa().toLowerCase().contains(empresaNorm))
                .collect(Collectors.toList());

        String[] sortParts = sort.split(",");
        String field = sortParts[0].trim().toLowerCase();
        boolean asc = sortParts.length < 2 || !"desc".equalsIgnoreCase(sortParts[1].trim());

        Comparator<Oferta> cmp;
        if ("empresa".equals(field)) {
            cmp = Comparator.comparing(Oferta::getEmpresa, String.CASE_INSENSITIVE_ORDER);
        } else if ("estado".equals(field)) {
            cmp = Comparator.comparing(Oferta::getEstado, String.CASE_INSENSITIVE_ORDER);
        } else if ("id".equals(field)) {
            cmp = Comparator.comparing(Oferta::getId);
        } else {
            cmp = Comparator.comparing(Oferta::getTitulo, String.CASE_INSENSITIVE_ORDER);
        }
        if (!asc) {
            cmp = cmp.reversed();
        }
        filtradas.sort(cmp);

        int from = Math.max(0, page * size);
        int to = Math.min(filtradas.size(), from + size);
        List<Oferta> pageContent = (from >= to) ? List.of() : filtradas.subList(from, to);

        Map<String, Object> body = new HashMap<>();
        body.put("content", pageContent);
        body.put("page", page);
        body.put("size", size);
        body.put("totalElements", filtradas.size());
        body.put("totalPages", (int) Math.ceil((double) filtradas.size() / size));
        body.put("sort", sort);

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oferta> getById(@PathVariable Long id) {
        return ofertas.stream()
                .filter(o -> Objects.equals(o.getId(), id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<Oferta> create(@Valid @RequestBody CreateOfertaRequest req) {
        Long id = seq.incrementAndGet();
        String estadoFinal = (req.getEstado() == null || req.getEstado().isBlank()) ? "ABIERTA" : req.getEstado();
        Oferta nueva = new Oferta(id, req.getTitulo(), req.getDescripcion(), req.getEmpresa(), estadoFinal);
        ofertas.add(nueva);
        return ResponseEntity.created(URI.create("/api/ofertas/" + id)).body(nueva);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<Oferta> update(@PathVariable Long id, @Valid @RequestBody UpdateOfertaRequest req) {
        Optional<Oferta> opt = ofertas.stream().filter(o -> Objects.equals(o.getId(), id)).findFirst();
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Oferta o = opt.get();
        if (req.getTitulo() != null && !req.getTitulo().isBlank()) o.setTitulo(req.getTitulo());
        if (req.getDescripcion() != null && !req.getDescripcion().isBlank()) o.setDescripcion(req.getDescripcion());
        if (req.getEmpresa() != null && !req.getEmpresa().isBlank()) o.setEmpresa(req.getEmpresa());
        if (req.getEstado() != null && !req.getEstado().isBlank()) o.setEstado(req.getEstado());
        return ResponseEntity.ok(o);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_write')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = ofertas.removeIf(o -> Objects.equals(o.getId(), id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
