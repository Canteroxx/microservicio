package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "job_offers",
    indexes = {
        @Index(name = "idx_job_offer_titulo", columnList = "titulo"),
        @Index(name = "idx_job_offer_empresa", columnList = "empresa"),
        @Index(name = "idx_job_offer_region", columnList = "region"),
        @Index(name = "idx_job_offer_comuna", columnList = "comuna"),
        @Index(name = "idx_job_offer_rubro", columnList = "rubro"),
        @Index(name = "idx_job_offer_tipo_contrato", columnList = "tipo_contrato"),
        @Index(name = "idx_job_offer_fecha_pub", columnList = "fecha_publicacion")
    }
)
@Data
public class JobOfferJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, length = 180)
    private String titulo;

    @Column(nullable = false, length = 120)
    private String empresa;

    @Column(nullable = false, length = 16) 
    private String estado;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50)
    private String region;

    @Column(length = 80)
    private String comuna;

    @Column(length = 80)
    private String rubro;

    @Column(name = "jornada", length = 40)
    private String jornadaLaboral;

    @Column(name = "tipo_contrato", length = 40)
    private String tipoContrato;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDateTime fechaPublicacion;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
