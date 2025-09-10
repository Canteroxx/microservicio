package cl.bne.empleos.ms_buscar_empleos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuscarOfertaRequest {
    private String palabraClave;
    private LocalDate fechaPublicacion;
    private List<String> regiones;
    private List<String> comunas;
    private List<String> ocupaciones;
    private List<String> gruposEmpleo;
    private List<String> nivelesEducativos;
    private List<String> jornadasLaborales;
    private List<String> tiposContrato;
    private List<String> origenesOferta;
    private Boolean discapacidad;
}
