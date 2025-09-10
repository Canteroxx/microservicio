package cl.bne.empleos.ms_buscar_empleos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuscarOfertaResponse {                   
    private String titulo;               
    private String descripcion;           
    private String empresa;
    private LocalDate fechaPublicacion;
    private String region;                
    private String comuna;               
    private String ocupacion;
    private String grupoEmpleo;
    private String nivelEducativo;
    private String jornadaLaboral;    
    private String tipoContrato;           
    private String origenOferta;
    private Boolean discapacidad;
}
