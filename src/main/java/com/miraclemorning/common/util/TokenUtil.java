package com.miraclemorning.common.util;

import com.miraclemorning.common.security.constants.SecurityConstants;
import io.jsonwebtoken.*;
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

        return Jwts.builder()
            .setSubject(invitationId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 864000000))
            .signWith( SignatureAlgorithm.HS512, SecurityConstants.JWT_INVITE_SECRET)
            .compact();
    }

    public String getInfoFromToken(String jwtToken){
        try{

            Jws<Claims> parsedToken = Jwts.parser().setSigningKey(SecurityConstants.JWT_INVITE_SECRET).parseClaimsJws(jwtToken);
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
            Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConstants.JWT_INVITE_SECRET).parseClaimsJws(jwtToken);
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
