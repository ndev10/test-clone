package helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.jwt.crypto.sign.MacSigner;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JWTHelper {


    public static String generateDummyUserTokenToken(String secret ) {

        Claims claims = Jwts.claims();
        claims.put("email", "xyz@email.com");
        claims.put("userId", "1");
        claims.put("firstName", "John");
        claims.put("lastName", "Doe");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 300 *1000))
                .signWith(SignatureAlgorithm.HS256,new SecretKeySpec(secret.getBytes(), "HMACSHA256"))
                .compact();
    }
}
