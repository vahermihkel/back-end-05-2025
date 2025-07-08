package ee.mihkel.webshop.security;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.PersonRole;
import ee.mihkel.webshop.model.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    public AuthToken generateToken(Person person) {
        AuthToken token = new AuthToken();

        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 60);

        String superSecretKey = "7shkEZCBEW2ufZvrCiijn_o2eOMAzJaQX88Ej9TMm_s";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

        String jwtToken = Jwts.builder()
                .signWith(secretKey)
                .subject(person.getEmail())
//                .content(person.getPersonRole().toString())
                .id(person.getId().toString())
                .issuer(person.getRole().toString())
                .expiration(expiration)
                        .compact();

        token.setToken(jwtToken);
        return token;
    }

    public Person parseToken(String token) {

        Person person = new Person();

        String superSecretKey = "7shkEZCBEW2ufZvrCiijn_o2eOMAzJaQX88Ej9TMm_s";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        person.setRole(PersonRole.valueOf(claims.getIssuer()));
        person.setEmail(claims.getSubject());
        person.setId(Long.valueOf(claims.getId()));

        return  person;
    }
}

// logib sisse ---> väljastab tokeni 31321321jkj4k.asdad21312.sdasdsad
// tokeni genereerisime ise: email, kasutajaID, roll, saladus-secret