package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobOfferJpaRepository extends JpaRepository<JobOfferJpaEntity, Long> {
    @Query("""
        SELECT o
        FROM JobOfferJpaEntity o
        WHERE (:q IS NULL OR :q = '' OR
               LOWER(o.titulo) LIKE LOWER(CONCAT('%', :q, '%')) OR
               LOWER(o.empresa) LIKE LOWER(CONCAT('%', :q, '%')) OR
               LOWER(o.descripcion) LIKE LOWER(CONCAT('%', :q, '%')))
          AND (:region IS NULL OR :region = '' OR o.region = :region)
          AND (:comuna IS NULL OR :comuna = '' OR o.comuna = :comuna)
          AND (:rubro IS NULL OR :rubro = '' OR o.rubro = :rubro)
          AND (:jornada IS NULL OR :jornada = '' OR o.jornadaLaboral = :jornada)
          AND (:tipoContrato IS NULL OR :tipoContrato = '' OR o.tipoContrato = :tipoContrato)
          AND (:estado IS NULL OR :estado = '' OR o.estado = :estado)
    """)
    Page<JobOfferJpaEntity> search(
            @Param("q") String q,
            @Param("region") String region,
            @Param("comuna") String comuna,
            @Param("rubro") String rubro,
            @Param("jornada") String jornada,
            @Param("tipoContrato") String tipoContrato,
            @Param("estado") String estado,
            Pageable pageable);
}
