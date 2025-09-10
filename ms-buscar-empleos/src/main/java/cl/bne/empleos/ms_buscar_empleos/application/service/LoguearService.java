package cl.bne.empleos.ms_buscar_empleos.application.service;

import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearResponse;
import cl.bne.empleos.ms_buscar_empleos.application.mapper.LoguearMapper;
import cl.bne.empleos.ms_buscar_empleos.application.usecase.Loguear;
import cl.bne.empleos.ms_buscar_empleos.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoguearService implements Loguear {

    private final UsuarioRepository postulanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoguearMapper loguearMapper;

    @Override
    public LoguearResponse loguear(LoguearRequest request) {
        return postulanteRepository.findByRut(request.getRut())
                .map(usuario -> {
                    if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                        throw new IllegalArgumentException("Contraseña incorrecta");
                    }
                    String token = jwtService.generateToken(usuario.getRut());
                    return loguearMapper.toResponse(usuario, token);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario no registrado"));
    }
}
