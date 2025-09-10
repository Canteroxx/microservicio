package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa;

import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByRut(String rut);
}

