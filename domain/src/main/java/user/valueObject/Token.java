package user.valueObject;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.UUID;
import java.util.Objects;

public class Token {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String token;

    // Generate token with random UUID as subject
    public Token() {
        this.token = Jwts.builder()
                .setSubject(UUID.randomUUID().toString().substring(0,8)) // Random subject
                .signWith(SECRET_KEY)
                .compact();
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }
}