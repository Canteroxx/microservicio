package cl.bne.empleos.ms_buscar_empleos.application.usecase;

import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearResponse;

public interface Loguear {
    LoguearResponse loguear(LoguearRequest datos);
}
