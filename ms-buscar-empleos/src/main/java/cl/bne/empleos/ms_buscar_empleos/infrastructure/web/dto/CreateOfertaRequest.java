package cl.bne.empleos.ms_buscar_empleos.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateOfertaRequest {
    @NotBlank private String titulo;
    @NotBlank private String descripcion;
    @NotBlank private String empresa;
    @Pattern(regexp = "ABIERTA|CERRADA", message = "estado debe ser ABIERTA o CERRADA")
    private String estado;
}
