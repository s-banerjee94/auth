package com.secu.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.util.Optional;

@Log4j2
public class JwtUtils {
    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private JwtUtils() {

    }

    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    private static Optional<Claims> parseToken(String jwtToken) {
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken).getPayload());
        } catch (JwtException | IllegalArgumentException jwtException) {
            log.error("Invalid token");
        }
        return Optional.empty();
    }

    public static Optional<String> getUsernameFromToken(String jwtToken) {
        Optional<Claims> claimsOptional = parseToken(jwtToken);

        return claimsOptional.map(Claims::getSubject);
    }
}
