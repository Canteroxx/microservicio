package cl.bne.empleos.ms_buscar_empleos.domain.model;

import java.time.LocalDate;
import java.util.List;

public record OfertaCriterio(
    String palabraClave,
    LocalDate fechaPublicacion,
    List<String> regiones,
    List<String> comunas,
    List<String> ocupaciones,
    List<String> gruposEmpleo,
    List<String> nivelesEducativos,
    List<String> jornadasLaborales,
    List<String> tiposContrato,
    List<String> origenesOferta,
    Boolean discapacidad
) {}
