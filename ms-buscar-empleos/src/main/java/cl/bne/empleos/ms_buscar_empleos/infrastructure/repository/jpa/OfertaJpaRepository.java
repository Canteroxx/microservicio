package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa;

import cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.entity.OfertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OfertaJpaRepository extends JpaRepository<OfertaEntity, Long> {

    @Query("SELECT o FROM OfertaEntity o " +
           "WHERE (:palabraClave IS NULL OR o.titulo LIKE %:palabraClave% OR o.descripcion LIKE %:palabraClave%) " +
           "AND (:fechaPublicacion IS NULL OR o.fechaPublicacion >= :fechaPublicacion) " + 
           "AND (:regiones IS NULL OR o.region IN :regiones) " +
           "AND (:comunas IS NULL OR o.comuna IN :comunas) " +
           "AND (:ocupaciones IS NULL OR o.ocupacion IN :ocupaciones) " +
           "AND (:gruposEmpleo IS NULL OR o.grupoEmpleo IN :gruposEmpleo) " +
           "AND (:nivelesEducativos IS NULL OR o.nivelEducativo IN :nivelesEducativos) " +
           "AND (:jornadasLaborales IS NULL OR o.jornadaLaboral IN :jornadasLaborales) " +
           "AND (:tiposContrato IS NULL OR o.tipoContrato IN :tiposContrato) " +
           "AND (:origenesOferta IS NULL OR o.origenOferta IN :origenesOferta) " +
           "AND (:discapacidad IS NULL OR o.discapacidad = :discapacidad)")
    List<OfertaEntity> buscarPorCriterios(
            @Param("palabraClave") String palabraClave,
            @Param("fechaPublicacion") LocalDate fechaPublicacion,
            @Param("regiones") List<String> regiones,
            @Param("comunas") List<String> comunas,
            @Param("ocupaciones") List<String> ocupaciones,
            @Param("gruposEmpleo") List<String> gruposEmpleo,
            @Param("nivelesEducativos") List<String> nivelesEducativos,
            @Param("jornadasLaborales") List<String> jornadasLaborales,
            @Param("tiposContrato") List<String> tiposContrato,
            @Param("origenesOferta") List<String> origenesOferta,
            @Param("discapacidad") Boolean discapacidad
    );
}
