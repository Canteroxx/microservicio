package cl.bne.empleos.ms_buscar_empleos.application.dto;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Oferta;
import lombok.Data;
import java.util.List;

@Data
public class SearchJobsOutput {
    private List<Oferta> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private String sort;
}
