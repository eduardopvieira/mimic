package br.edu.ufersa.mimic.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthenticationService {
    static final long EXPIRATIONTIME = 1000*60*180; //deixei 3 horas pra expirar
    static final String SIGNINGKEY = "NkjqwnKWnqwJQNWiuhoQWhqwo7wWOQWGQHWQYUBWohjqwbjkJW";
    static final String PREFIX = "Bearer";
    private static final SecretKey SECRETKEY = Keys.hmacShaKeyFor(SIGNINGKEY.getBytes());

    static public void addToken(HttpServletResponse res, String email) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATIONTIME);
        String JWT = io.jsonwebtoken.Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRETKEY)
                .compact();
        res.addHeader("Authorization", PREFIX + " " + JWT);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    static public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            if (token.startsWith(PREFIX)) token = token.substring(PREFIX.length()).trim();
            else throw new MalformedJwtException("Invalid Authorization header format");
            Claims claims = Jwts.parser()
                    .verifyWith(SECRETKEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String email = claims.get("sub", String.class);
            if (email != null)
                return new org.springframework.security.authentication.
                        UsernamePasswordAuthenticationToken(
                                email, null, java.util.Collections.emptyList());
        }
        return null;
    }

}
