package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostulanteJpaRepository extends JpaRepository<PostulanteJpaEntity, Long> {
    // Si quieres agregar consultas personalizadas, las defines aquí
}
