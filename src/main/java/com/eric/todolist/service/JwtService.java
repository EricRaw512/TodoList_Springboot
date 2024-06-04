package com.eric.todolist.service;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eric.todolist.entity.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {
    
	 //Don't Store in here
	 String privateKeyString = "MGACAQAwEAYHKoZIzj0CAQYFK4EEACMESTBHAgEBBEIAZVBnNC+hGUo4J3fPs0FRluqJqMGggylZ1Gig2V/kNySqM9UndolJBD6QONFOLUSszyg01BA4Z6WArpdGMWmXX4k=";
	 String publicString = "MIGbMBAGByqGSM49AgEGBSuBBAAjA4GGAAQAUyvn+anazFYKW01cLKxXe+btV0U79bybIJvLT2NXAO9QodMj3ByJ76xk3V/PsC0ex1fKjT+V08RbKw8y9jpS70EAqR7zuwY9cXhpCoQL99mbL1uQaotUy0tRKSwrNUJLS9OhnoBuHEsmCu3MJ3xWHcHNCuy5wd3Sr5NLR8o6TH4I0c4=";
	 private long expiration= 6_000_000;

    public String generateToken(User user) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRole());
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getPrivateSignKey(), SignatureAlgorithm.ES512)
                .compact();
    }
    
    private Key getPublicSignKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
	     byte[] encodedPublicKeyBytes = Decoders.BASE64.decode(publicString);
	     KeyFactory keyFactory = KeyFactory.getInstance("EC");
		 return keyFactory.generatePublic(new X509EncodedKeySpec(encodedPublicKeyBytes));
    }

    private Key getPrivateSignKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
		 byte[] encodedPrivateKeyBytes = Decoders.BASE64.decode(privateKeyString);
		 KeyFactory keyFactory = KeyFactory.getInstance("EC");
		 return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKeyBytes)); 
    }

    public boolean validateToken(String token, UserDetails userDetails) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        Date expirationDate = extractExpiration(token);
        if (expirationDate.before(new Date())) {
            return false;
        }

        String username = extractUsername(token);
        return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
    }

    public String extractUsername(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        return Jwts.parserBuilder()
        .setSigningKey(getPublicSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
}
    
    public Date extractExpiration(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        return Jwts.parserBuilder()
        .setSigningKey(getPublicSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
    }
}
