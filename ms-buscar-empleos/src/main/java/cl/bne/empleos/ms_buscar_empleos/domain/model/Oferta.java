package cl.bne.empleos.ms_buscar_empleos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oferta {
    private Long id;
    private String titulo;
    private String descripcion;
    private String empresa;
    private String estado; // ABIERTA | CERRADA
}
