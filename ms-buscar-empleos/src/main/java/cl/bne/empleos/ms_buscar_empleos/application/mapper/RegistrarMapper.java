package cl.bne.empleos.ms_buscar_empleos.application.mapper;

import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarResponse;
import cl.bne.empleos.ms_buscar_empleos.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrarMapper{

    @Mapping(target = "id", ignore = true)
    Usuario toUsuario(RegistrarRequest request);
    RegistrarResponse toResponse(Usuario response);
}
