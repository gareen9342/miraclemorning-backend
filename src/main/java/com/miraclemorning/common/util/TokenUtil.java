package com.miraclemorning.common.util;

import com.miraclemorning.common.security.constants.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@NoArgsConstructor
public class TokenUtil {
    public String createToken(String invitationId){
        byte[] signingKey = getSigningKey();

        String token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
            .claim("invitationId", invitationId)
            .compact();

        return token;
    }

    public String getInfoFromToken(String jwtToken){
        try{

            Jws<Claims> parsedToken = Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_INVITE_SECRET).build().parseClaimsJws(jwtToken);
            Claims claims = parsedToken.getBody();

            return (String) claims.get("invitationId");
        }catch(ExpiredJwtException exception){
            log.warn("token is expired");
        }catch(UnsupportedJwtException exception){
            log.warn("Request to parse unsupported KWT : {}, failed : {}", jwtToken, exception.getMessage());
        }catch(MalformedJwtException exception){
            log.warn("Request to parse invalid JWT : {}, failed : {}", jwtToken, exception.getMessage());
        }catch(SignatureException exception){
            log.warn("Request to parse JWT with invalid signature : {}, failed : {}", jwtToken, exception.getMessage());
        }catch(IllegalArgumentException exception){
            log.warn("Request to parse empty or null JWT : {} failed : {}", jwtToken, exception.getMessage() );
        }
        return null;
    }

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_INVITE_SECRET).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException exception){
            log.error("Token expired");
            return false;
        } catch (NumberFormatException exception){
            log.error("Token is Null");
            return false;
        }
    }
    public byte[] getSigningKey(){
        return SecurityConstants.JWT_INVITE_SECRET.getBytes();
    }
}
