package io.github.bhavuklabs.sleepnswipebackend.security.config.validators;

import io.github.bhavuklabs.sleepnswipebackend.security.config.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JwtValidator extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);
            if(jwt != null) {
                validateAndSetAuthentication(jwt);
            }

            filterChain.doFilter(request, response);
        } catch(Exception e) {
            handleJwtValidationError(response, e);
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private void validateAndSetAuthentication(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(
                JwtConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8)
        );

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String email = String.valueOf(claims.get("email"));
        String userId = claims.get("userId", String.class);

        Object authoritiesObject = claims.get("authorities");
        List<GrantedAuthority> authorityList;

        if(authoritiesObject == null) {
            authorityList = List.of();
        } else if(authoritiesObject instanceof String) {
            authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList((String) authoritiesObject);
        } else if(authoritiesObject instanceof List<?>) {
            authorityList = ((List<?>) authoritiesObject).stream()
                    .map(Object::toString)
                    .map(AuthorityUtils::createAuthorityList)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } else if(authoritiesObject instanceof String[]) {
            authorityList = AuthorityUtils.createAuthorityList((String[]) authoritiesObject);
        } else {
            throw new BadCredentialsException("Invalid Authorities Format");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleJwtValidationError(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{error: %s }", e.getMessage()));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/public") ||
                path.startsWith("/login") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs");
    }
}
