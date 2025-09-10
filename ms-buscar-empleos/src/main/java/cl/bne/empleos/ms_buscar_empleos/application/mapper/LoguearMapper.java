package cl.bne.empleos.ms_buscar_empleos.application.mapper;

import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearResponse;
import cl.bne.empleos.ms_buscar_empleos.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoguearMapper {
    @Mapping(target = "token", source = "token")
    LoguearResponse toResponse(Usuario usuario, String token);
}
