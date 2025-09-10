package cl.bne.empleos.ms_buscar_empleos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoguearRequest {
    private String rut;
    private String password;
}
