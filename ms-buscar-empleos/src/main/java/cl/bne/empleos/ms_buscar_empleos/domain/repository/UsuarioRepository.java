package cl.bne.empleos.ms_buscar_empleos.domain.repository;

import cl.bne.empleos.ms_buscar_empleos.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByRut(String rut);
    Usuario save(Usuario postulante);
}
