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

    // Generate a new random token
    public Token() {
        this.token = Jwts.builder()
                .setSubject(UUID.randomUUID().toString().substring(0, 8))
                .signWith(SECRET_KEY)
                .compact();
    }

    // ✅ Reconstruct token from a known string (e.g., from logout request)
    public Token(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token string cannot be null or empty");
        }
        this.token = token;
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
