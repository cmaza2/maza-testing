package com.maza.peoplemanagementservice.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private final static String ACCESS_TOKEN="1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7a8b9c0d1e2f3g";
    private final static Long ACCESS_TOKEN_vALIDITY_sEOONDS=2_592_000L;

    public static String createToken(String name,String identification){
        long expiration =ACCESS_TOKEN_vALIDITY_sEOONDS * 1000;
        Date expirationDate=new Date(System.currentTimeMillis()+expiration);
        Map<String, Object> extra = new HashMap<>();
        extra.put("name",name);
        return Jwts.builder()
                .setSubject(identification)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(SignatureAlgorithm.HS256,ACCESS_TOKEN.getBytes())
                .compact();
    }
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String identification = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(identification, null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
        }
}
