package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.mapper;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.entity.OfertaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfertaMapper {

    Oferta toDomain(OfertaEntity entity);
    OfertaEntity toEntity(Oferta domain);
}
