//package AMS.ams.UTIL;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.function.Function;
//import java.util.Base64;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import javax.crypto.SecretKey;
//
//@Component
//public class JwtTokenUtil {
//
//    private SecretKey secretKey;
//
//    @Value("${jwt.secret}")
//    public void setSecret(String secret) {
//        // Decode the Base64 encoded key and set it as SecretKey
//        byte[] decodedKey = Base64.getDecoder().decode(secret);
//        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
//    }
//
//    @Value("${jwt.expiration}")
//    private long expiration;
//
//    public String generateToken(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return Jwts.builder()
//            .setSubject(userDetails.getUsername())
//            .claim("roles", userDetails.getAuthorities())
//            .setIssuedAt(new Date())
//            .setExpiration(new Date(System.currentTimeMillis() + expiration))
//            .signWith(secretKey, SignatureAlgorithm.HS512)
//            .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//        } catch (Exception e) {
//            // Log the error for debugging purposes
//            System.err.println("Error parsing JWT: " + e.getMessage());
//            throw e;
//        }
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}
