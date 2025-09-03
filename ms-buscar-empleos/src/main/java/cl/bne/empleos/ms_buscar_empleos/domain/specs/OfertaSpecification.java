package cl.bne.empleos.ms_buscar_empleos.domain.specs;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import cl.bne.empleos.ms_buscar_empleos.application.dto.SearchJobsInput;

import java.util.function.Predicate;

public class OfertaSpecification {
    public static Predicate<Oferta> fromSearchInput(SearchJobsInput input) {
        return oferta ->
                (input.getQuery().isEmpty() ||
                        oferta.getTitulo().toLowerCase().contains(input.getQuery().toLowerCase()) ||
                        oferta.getDescripcion().toLowerCase().contains(input.getQuery().toLowerCase()) ||
                        oferta.getEmpresa().toLowerCase().contains(input.getQuery().toLowerCase())) &&
                        (input.getEstado().isEmpty() || oferta.getEstado().equalsIgnoreCase(input.getEstado())) &&
                        (input.getEmpresa().isEmpty() || oferta.getEmpresa().equalsIgnoreCase(input.getEmpresa()));
    }
}
