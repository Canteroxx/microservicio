package cl.bne.empleos.ms_buscar_empleos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Postulacion {
    private Long id;
    private Long postulanteId;
    private Long ofertaId;
    private String estado; // POSTULADO | EN_REVISION | ENTREVISTA | etc.
    private LocalDateTime fecha;
}
