package com.deeper.homework.service;

import com.deeper.homework.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long expiration;

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    claims.put("email", user.getEmail());
    claims.put("roles", user.getAuthorities());
    return createToken(claims, user.getUsername());
  }

  private String createToken(Map<String, Object> claims, String username) {
    return Jwts.builder()
        .claims(claims)
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignKey(), Jwts.SIG.HS256)
        .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    Date expirationDate = extractExpiration(token);
    if (expirationDate.before(new Date())) {
      return false;
    }
    String username = extractUsername(token);
    return userDetails.getUsername().equals(username) && !expirationDate.before(new Date());
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractUsername(String token) {
    return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload()
        .getSubject();
  }

  public Date extractExpiration(String token) {
    return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload()
        .getExpiration();
  }
}