package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Usuario;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.UsuarioRepository;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.entity.UsuarioEntity;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa.UsuarioJpaRepository;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Optional<Usuario> findByRut(String rut) {
        return jpaRepository.findByRut(rut).map(usuarioMapper::toDomain);
    }

    @Override
    public Usuario save(Usuario postulante) {
        UsuarioEntity entity = usuarioMapper.toEntity(postulante);
        return usuarioMapper.toDomain(jpaRepository.save(entity));
    }
}
