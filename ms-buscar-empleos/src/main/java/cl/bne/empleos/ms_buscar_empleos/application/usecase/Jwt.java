package cl.bne.empleos.ms_buscar_empleos.application.usecase;

public interface Jwt {
    String generateToken(String rut);
    String extractRut(String token);
    boolean isTokenValid(String token);
}