package cl.bne.empleos.ms_buscar_empleos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegistrarRequest {
    private String nombres;
    private String apellidos;
    private String rut;
    private String email;
    private String password; 
}
