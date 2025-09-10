package cl.bne.empleos.ms_buscar_empleos.application.usecase;

import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarResponse;

public interface Registrar {
    RegistrarResponse registrar(RegistrarRequest postulante);
}
