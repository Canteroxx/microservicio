package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Postulante;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.PostulanteRepository;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa.PostulanteJpaEntity;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa.PostulanteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostulanteRepositoryImpl implements PostulanteRepository {

    private final PostulanteJpaRepository jpaRepository;

    public PostulanteRepositoryImpl(PostulanteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Postulante save(Postulante postulante) {
        PostulanteJpaEntity e = new PostulanteJpaEntity();
        e.setId(postulante.getId()); // importante para update
        e.setNombre(postulante.getNombre());
        e.setEmail(postulante.getEmail());
        e = jpaRepository.save(e);
        return new Postulante(e.getId(), e.getNombre(), e.getEmail());
    }

    @Override
    public Optional<Postulante> findById(Long id) {
        return jpaRepository.findById(id)
                .map(e -> new Postulante(e.getId(), e.getNombre(), e.getEmail()));
    }

    @Override
    public List<Postulante> findAll() {
        return jpaRepository.findAll().stream()
                .map(e -> new Postulante(e.getId(), e.getNombre(), e.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
