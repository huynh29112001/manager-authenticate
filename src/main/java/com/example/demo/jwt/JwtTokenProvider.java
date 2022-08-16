package com.example.demo.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.ErrorManager;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "huynh";
    private final long JWT_EXPIRATION = 1000*60*60*24L;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(expiryDate).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
