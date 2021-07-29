package com.miraclemorning.common.security.jwt.filter;

import com.miraclemorning.common.security.jwt.constants.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 인가하는 필터임
 * 모든 HTTP요청을 처리하면서 토큰이 만기 되지 않았거나 서명키가 올바른 토큰이 있느 AUthorization 헤더가 있는 지 확인함
 * 토큰이 유효하면 필터는 인증 데이터를 Spring 의 보안 컨텍스트에 추가함.
 */

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER); // authorization header request에서 가지고 옴...
        if(isNotEmpty(token)){
            try{
                byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

                // parser()가 depecated됐다고 해서 parserBuilder로 변경...
                //이거.. Jwts 들어가서 읽어보는 것 별거없으니 헷갈리면 일어보기 ...
                Jws<Claims> parsedToken = Jwts
                        .parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(token.replace("Bearer", ""));

                String username = parsedToken.getBody().getSubject(); // 파싱된 토큰에서 바디값 가지고 오는

                List<SimpleGrantedAuthority> authorities = ((List<?>)parsedToken.getBody()
                        .get("rol"))
                        .stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());

                if(isNotEmpty(username)){
                    // 현재 username은 email에 해당...
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            }catch(ExpiredJwtException exception){
                log.warn("유효하지 않은 토큰 JWT : {} failed : {} ", token, exception.getMessage());
            }catch(UnsupportedJwtException exception){
                log.warn("Request to parse unsupported KWT : {}, failed : {}", token, exception.getMessage());
            }catch(MalformedJwtException exception){
                log.warn("Request to parse invalid JWT : {}, failed : {}", token, exception.getMessage());
            }catch(SignatureException exception){
                log.warn("Request to parse JWT with invalid signature : {}, failed : {}", token, exception.getMessage());
            }catch(IllegalArgumentException exception){
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage() );
            }
        }
        return null;
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
