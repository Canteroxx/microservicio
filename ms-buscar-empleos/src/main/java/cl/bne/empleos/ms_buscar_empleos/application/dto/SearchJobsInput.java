package cl.bne.empleos.ms_buscar_empleos.application.dto;

import lombok.Data;

@Data
public class SearchJobsInput {
    private String query = "";
    private String estado = "";
    private String empresa = "";
    private int page = 0;
    private int size = 10;
    private String sortBy = "titulo";
    private String sortDirection = "asc";
}
