package cl.bne.empleos.ms_buscar_empleos.infrastructure.controller;

import cl.bne.empleos.ms_buscar_empleos.application.usecase.Registrar;
import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.LoguearResponse;
import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarRequest;
import cl.bne.empleos.ms_buscar_empleos.application.dto.RegistrarResponse;
import cl.bne.empleos.ms_buscar_empleos.application.usecase.Loguear;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postulantes")
@RequiredArgsConstructor
public class UsuarioController {

    private final Registrar registrar;
    private final Loguear loguear;

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrar(@RequestBody RegistrarRequest request) {
        try {
            RegistrarResponse creado = registrar.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoguearRequest request) {
        try {
            LoguearResponse token = loguear.loguear(request);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
