package br.edu.ufersa.mimic.service.auth;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class AuthenticationService {
    static final long EXPIRATIONTIME = 1000*60*180; //3 horas pra expirar
    static final String SIGNINGKEY = "NkjqwnKWnqwJQNWiuhoQWhqwo7wWOQWGQHWQYUBWohjqwbjkJW";
    static final String PREFIX = "Bearer";
    private static final SecretKey SECRETKEY = Keys.hmacShaKeyFor(SIGNINGKEY.getBytes());

}
