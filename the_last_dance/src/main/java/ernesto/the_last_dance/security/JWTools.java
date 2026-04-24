package ernesto.the_last_dance.security;

import ernesto.the_last_dance.entities.Utente;
import ernesto.the_last_dance.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTools {

    @Value("${jwt.secret}")
    private String secret;

    public String generatedToken(Utente utente){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7) )
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
    public void verifyToken(String token){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex){
            throw new UnauthorizedException("Token non valido, effettuare di nuovo il login");
        }
    }

    public UUID getIdByToken(String token){
        try{
            return  UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject());
        } catch (Exception ex){
            throw new UnauthorizedException("Token non valido, effettuare di nuovo il login");
        }
    }
}
