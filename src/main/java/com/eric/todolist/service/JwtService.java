package com.eric.todolist.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.eric.todolist.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {
    
    //Store it in environment not here! ok
    private String secretKey = "402967675642d720aad80db564db2aef6fc7a8de33b5f3b064ff02df705901075834552b5168fbca7407201329cec7b42bd7a0b13da56fa3c4ce5526a4069919f05dfa422b5af40c7da80d1504829b1877df4abe7b20d53dbc6e185bacb1841e5272fd0edff69c621744206962e1f7893707cdc6e7d0766ded9a2b8dd9999f718c380dc40bc585ad5b026493c7de131104394d8152bc9ce0fbd00916d27d91906266aba996c70d044c4fa97e7561ae204680bbe7e3d1ff8e68e22f79745db6ea4895b4cfc05682a724d644aaed2179185792505a163ebe20b3bf7bf176686a5c67eba884ea9674031b2d34320755d30fdf5ba2b2763af5e2d04ca1868426d8fd";
    private long expiration= 120_000;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getAuthorities());
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        Date expirationDate = extractExpiration(token);
        if (expirationDate.before(new Date())) {
            return false;
        }

        String username = extractUsername(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
}
    
    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
    }
}
