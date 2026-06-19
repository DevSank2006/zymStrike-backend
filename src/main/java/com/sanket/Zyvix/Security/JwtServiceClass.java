package com.sanket.Zyvix.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtServiceClass {
    private static final String SECRET="Zyvix_Dev_Sanket_2026_b7F#9mK@2xP!rL8$QwN6^TaJ3zV&HsE5dY1";
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .claim("role","USER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000L *60*60*24*7))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
    public Claims verifyTokenAndExtractClaims(String token){
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token).getBody();
    }
    public String extractUserName(String token){
        return verifyTokenAndExtractClaims(token).getSubject();
    }
    public Date getExpirationTime(String token){
        return verifyTokenAndExtractClaims(token).getExpiration();
    }
    public Boolean isExpired(String token){
        if(getExpirationTime(token).toInstant().isBefore(new Date().toInstant())){
            return true;
        }
        return false;
    }
}
