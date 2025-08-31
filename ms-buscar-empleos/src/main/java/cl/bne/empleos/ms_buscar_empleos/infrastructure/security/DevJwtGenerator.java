package cl.bne.empleos.ms_buscar_empleos.infrastructure.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.time.Instant;
import java.util.Date;

public class DevJwtGenerator {
    public static String generate(String secret, String subject, String issuer, String audience, long ttlSeconds) throws Exception {
        JWSSigner signer = new MACSigner(secret.getBytes());
        Instant now = Instant.now();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .audience(audience)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(ttlSeconds)))
                .claim("scope", "read write")
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public static void main(String[] args) throws Exception {
        String token = generate(
                "super-secreto-cambia-esto-para-produccion-0123456789",
                "juan.munoz",
                "bne-ms",
                "bne-clients",
                3600
        );
        System.out.println(token);
    }
}
