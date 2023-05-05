/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miha.zika.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import miha.zika.entity.UserBlogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTtokenProvider {
    private static final Logger LOG=LoggerFactory.getLogger(JWTtokenProvider.class);
    
    public String generateToken (Authentication authentication){
        UserBlogger user = (UserBlogger) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date after = new Date(now.getTime() + SecurityConst.EXPIRATION_TIME);
        String userId=Long.toString(user.getId());
        Map <String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", user.getEmail());
        claims.put("firstname", user.getName());
        claims.put("lastname", user.getLastname());
        
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(after)
                .signWith(SignatureAlgorithm.HS512, SecurityConst.SECRET_CODE).compact();
    }
    
    public boolean validateToken (String token){
    try {Jwts.parser().setSigningKey(SecurityConst.SECRET_CODE).parseClaimsJws(token); return true;}
    catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex){
    LOG.error(ex.getMessage());
    return false;
    }
    
   }
    
    public Long getUserIdFromToken(String token){
    Claims claims = Jwts.parser().setSigningKey(SecurityConst.SECRET_CODE).parseClaimsJwt(token).getBody();
    String id=(String) claims.get("id");
    return Long.parseLong(id);
    }
}
