package com.miraclemorning.common.security.jwt.provider;

import com.miraclemorning.common.security.domain.CustomUser;
import com.miraclemorning.common.security.jwt.constants.SecurityConstants;
import com.miraclemorning.domain.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    public String createToken(long userId, String userEmail, List<String> roles){
        byte[] signingKey = getSigningKey();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE) // jwt 토큰임을 명시
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 시간 지정.
                .claim("rol", roles) // 바디 부분 지정 - 권한
                .claim("uid", userId) // 유저 아이디
                .claim("uemail", userEmail) // 이메일
                .compact();

        return token;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        if(isNotEmpty(tokenHeader)){
            try{
                byte[] signingKey = getSigningKey();

                // parser()가 depecated됐다고 해서 parserBuilder로 변경...
                //이거.. Jwts 들어가서 읽어보는 것 별거없으니 헷갈리면 일어보기 ...
                Jws<Claims> parsedToken = Jwts
                        .parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(tokenHeader.replace("Bearer ", ""));

                Claims claims = parsedToken.getBody(); // 파싱된 토큰에서 바디값 가지고 오는

                String userId = (String)claims.get("uid");
                String userEmail = (String)claims.get("uemail");

                List<SimpleGrantedAuthority> authorities = ((List<?>)claims
                        .get("rol"))
                        .stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                if(isNotEmpty(userEmail)){
                    Member member = new Member();

                    member.setId(Long.parseLong(userId));
                    member.setEmail(userEmail);
                    member.setPassword("");

                    UserDetails userDetails = new CustomUser(member, authorities);
                    return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                }
            }catch(ExpiredJwtException exception){
                log.warn("유효하지 않은 토큰 JWT : {} failed : {} ", tokenHeader, exception.getMessage());
            }catch(UnsupportedJwtException exception){
                log.warn("Request to parse unsupported KWT : {}, failed : {}", tokenHeader, exception.getMessage());
            }catch(MalformedJwtException exception){
                log.warn("Request to parse invalid JWT : {}, failed : {}", tokenHeader, exception.getMessage());
            }catch(SignatureException exception){
                log.warn("Request to parse JWT with invalid signature : {}, failed : {}", tokenHeader, exception.getMessage());
            }catch(IllegalArgumentException exception){
                log.warn("Request to parse empty or null JWT : {} failed : {}", tokenHeader, exception.getMessage() );
            }
        }
        return null;
    }

    public boolean validateToken(String jwtToken){

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.JWT_SECRET).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    public byte[] getSigningKey(){
        return SecurityConstants.JWT_SECRET.getBytes();
    }

    /**
     * 왜 이놈들은 직접 쓰는 것도 아니고.. 그렇다고 공통 유틸로 빼지도 않은 것인가...
     */
    private boolean isEmpty(final CharSequence cs){ // charsequence는 뭐일까ㅠㅠ
        return cs == null || cs.length() == 0;
    }

    private boolean isNotEmpty(final CharSequence cs){
        return !isEmpty(cs);
    }
}
