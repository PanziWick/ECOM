package com.example.ecommercedemo.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private static final String SECRET_KEY = "9cae5f9645cb8689a39587bffaf7464d2edd16ec1a5804dea36b0435042dd9a6cb8235a15062eb046db2cb7aa413886c3082b89c147e10828776467020a390fc02b8133a6a3199a263f052853f0d28f52abbde0b024b75da040565629f2a1034c48768188ac196aa408af4b7c9072b503d4c0e4f93604cff97428addb1f4d835fdddb95516a64f8aa77672635cb427b7f88ec7837986c9b60eb28a79ec374a76fc492c9879732769e0404d68afa620eb02aadcfe80014bfc89a444235c44b732fc8764e682d0114b20933778ab14177a635d2878544fb31b43b35567890546748216607a24c07d9b023af65b21d81f9cb802da7125aaf2e8840dc9c93f605c9f";
    private final JwtParser jwtParser;

    public JWTService() {
        this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Generate a Token
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid (String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    private Claims extractAllClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
