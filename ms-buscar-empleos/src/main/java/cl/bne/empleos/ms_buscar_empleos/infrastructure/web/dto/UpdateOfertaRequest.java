package cl.bne.empleos.ms_buscar_empleos.infrastructure.web.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateOfertaRequest {
    private String titulo;
    private String descripcion;
    private String empresa;

    @Pattern(regexp = "ABIERTA|CERRADA", message = "estado debe ser ABIERTA o CERRADA")
    private String estado;
}
