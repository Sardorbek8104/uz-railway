package uz.pdp.bookingservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Setter
@Service
@RequiredArgsConstructor
@Profile(value = "docker")
public class JwtService {

    @Value("${jwt.access.token.secretKey}")
    private String jwtAccessTokenSecretKey;


    private SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(jwtAccessTokenSecretKey.getBytes());
    }

    public String extractUsernameFromToken(String accessToken) {
        return Jwts.parser().verifyWith(getAccessTokenSecretKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject();
    }
}
