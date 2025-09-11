package cl.bne.empleos.ms_buscar_empleos.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ofertas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
