package io.github.bhavuklabs.sleepnswipebackend.security.config.providers;

import io.github.bhavuklabs.sleepnswipebackend.security.config.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtProvider {

    public String generateToken(Authentication authentication, UUID userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JwtConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("email", authentication.getName())
                .claim("userId", userId)
                .claim("authorities", authentication.getAuthorities())
                .signWith(Keys.hmacShaKeyFor(JwtConstants.JWT_SECRET.getBytes()))
                .compact();
    }

    public String getEmailFromToken(String jwt) {
        try {
            if(jwt.startsWith(JwtConstants.TOKEN_PREFIX)) {
                jwt = jwt.substring(7);
            }

            SecretKey key = Keys.hmacShaKeyFor(JwtConstants.JWT_SECRET.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("email", String.class);
        } catch(Exception e) {
            throw new BadCredentialsException("Invalid JWT");
        }
    }
}
