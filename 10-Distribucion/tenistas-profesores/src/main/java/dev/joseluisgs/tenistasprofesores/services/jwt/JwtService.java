package dev.joseluisgs.tenistasprofesores.services.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

// Servicio de JWT
@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refresh-token}")
    private long refreshExpiration;

    public String generateToken(
            UserDetails userDetails
    ) {
        return buildToken(userDetails, jwtExpiration);
    }


    // Genera un token refresh con los datos del usuario
    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(userDetails, refreshExpiration);
    }

    private String buildToken(
            UserDetails userDetails,
            long expiration
    ) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(getSignInKey());
    }

    private Algorithm getSignInKey() {
        // Si lo ponemos en base 64, lo convertimos a bytes
        // byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Si es un string, lo convertimos a bytes
        return Algorithm.HMAC512(secretKey);
    }

    private DecodedJWT extractAllClaims(String token) {
        return JWT.require(getSignInKey()).build().verify(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiresAt();
    }

}
