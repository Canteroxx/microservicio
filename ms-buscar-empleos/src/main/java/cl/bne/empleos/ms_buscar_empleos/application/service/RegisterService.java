package cl.bne.empleos.ms_buscar_empleos.application.service;

import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarResponse;
import cl.bne.empleos.ms_buscar_empleos.application.mapper.RegistrarMapper;
import cl.bne.empleos.ms_buscar_empleos.application.usecase.Registrar;
import cl.bne.empleos.ms_buscar_empleos.domain.model.Usuario;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService implements Registrar {

    private final UsuarioRepository postulanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrarMapper registrarMapper;

    @Override
    public RegistrarResponse registrar(RegistrarRequest request) {
        if (postulanteRepository.findByRut(request.getRut()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con este RUT");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        Usuario usuario = registrarMapper.toUsuario(request);

        Usuario usuarioRegistrado = postulanteRepository.save(usuario);

        return registrarMapper.toResponse(usuarioRegistrado);
    }
}
