package com.alx.auth.auth.jwt;

import com.alx.auth.user.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;


    public String createToken(Usuario user) {
        Map<String, Object> claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());

        // TODO remove hardcode
        claims.put("twitter_account", "@alx");

        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(now.getTime() + 3600 * 1000 * 24);

        return Jwts.builder()
                // TODO remove hardcode
                .setHeaderParam("company_name", "alx-codec_raft")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String jwt) {
        // el Subject deberia ser el   (email,uuid,username)    q vamos a setear en la construccion del JWT
        return extractClaim(jwt, Claims::getSubject);
    }

    public void validate(String token) {
        extractUsername(token);
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);   // jwt lo requiere en b64
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // genericos para extraer cualquier claim q nos interese
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);    // extra TODOS los claims del JWT q se le pase
    }

    private Claims extractAllClaims(String jwt) {
        Claims claims;

        try {
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey()) // JWT Secret
                    .build()    // xq es 1 builder
                    .parseClaimsJws(jwt)    // parse el JWT para extraer los claims
                    .getBody();     // cuando hace el parse puede obtener los claims y en este caso queremos el body
        } catch (Exception ex) {    // invalid signature
            throw new RuntimeException("Invalid Token");
        }

        return claims;
    }

}
