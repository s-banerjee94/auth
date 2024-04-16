package com.secu.auth.util;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class JwtUtils {
    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private static final String ISSUER = "sportiz";

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

    public static String generateToken(String username) {
        Date currenDate = new Date();
        Date expDate = DateUtils.addMinutes(currenDate, 10);
        return Jwts.builder().id(UUID.randomUUID().toString()).issuer(ISSUER)
                .subject(username).signWith(secretKey).issuedAt(currenDate).expiration(expDate).compact();
    }
}
