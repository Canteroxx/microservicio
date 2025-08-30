package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.jpa;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "postulantes")
@Data
public class PostulanteJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
}
