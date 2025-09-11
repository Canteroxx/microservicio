package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.mapper;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Usuario;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toDomain(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario domain);
}
