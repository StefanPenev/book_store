package com.example.book_store.util;

import com.example.book_store.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class JwtUtil {

    private Long tokenExpiration;
    private Key secretKey;

    @Autowired
    public JwtUtil(@Value(value = "${token.expiration:600}") Long tokenExpiration) {

        this.tokenExpiration = tokenExpiration;
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(UserDto userDto) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDto.getAuthorities());
        claims.put("userCreationDate", userDto.getCreatedAt().toString());

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now().plus(tokenExpiration, ChronoUnit.SECONDS)))
                .setIssuedAt(Date.from(Instant.now()))
                .setSubject(userDto.getUsername())
                .signWith(secretKey)
                .compact();

        return token;
    }

    public String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");

        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        return "";
    }

    public Set<String> getAuthorities(String token) {
        Claims body = getClaims(token);

        Object o = ((ArrayList) body.get("authorities")).get(0);
        String authorities = (String) o;

        return Set.of(authorities);
    }

    public String getUsernameFromToken(String token) {

        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) { ;

        Date expiredDate = getClaims(token).getExpiration();

        LocalDateTime expiredLocalDateTime = expiredDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return LocalDateTime.now().isAfter(expiredLocalDateTime);
    }

    private Claims getClaims(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return body;
    }
}

