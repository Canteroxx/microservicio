package cl.bne.empleos.ms_buscar_empleos.application;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Postulante;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.PostulanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostulanteService {

    private final PostulanteRepository repository;

    public PostulanteService(PostulanteRepository repository) {
        this.repository = repository;
    }

    public Postulante registrar(Postulante postulante) {
        return repository.save(postulante);
    }

    public List<Postulante> listar() {
        return repository.findAll();
    }

    public Optional<Postulante> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Postulante> actualizar(Long id, Postulante postulante) {
        return repository.findById(id).map(actual -> {
            actual.setNombre(postulante.getNombre());
            actual.setEmail(postulante.getEmail());
            return repository.save(actual);
        });
    }

    public boolean eliminar(Long id) {
        return repository.findById(id).map(p -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
