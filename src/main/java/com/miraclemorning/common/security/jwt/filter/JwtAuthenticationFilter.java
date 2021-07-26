package com.miraclemorning.common.security.jwt.filter;

import com.miraclemorning.common.security.domain.CustomUser;
import com.miraclemorning.common.security.jwt.constants.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UsernamePasswordAuthenticationFilter란?
 * username, password를 쓰는 form기반 인증을 처릴하는 필터
 * AuthenticationManager를 이용한 인증 실행
 * 성공하면 Authentication 객체를 SecutiryContext에 저장 후 AuthenticationSuccessHandler 실행
 * 실패하면 AuthenticationFailureHandler 실행
 *
 * https://docs.spring.io/spring-security/site/docs/3.0.x/apidocs/org/springframework/security/web/authentication/UsernamePasswordAuthenticationFilter.html
 *
 * 그런데 문제점이 있다함 username 을 email로 쓰게 될 경우 맥락이 맞지 않음
 * https://tech.junhabaek.net/spring-security-usernamepasswordauthenticationfilter%EC%9D%98-%EB%8D%94-%EA%B9%8A%EC%9D%80-%EC%9D%B4%ED%95%B4-8b5927dbc037#9559
 *
 * 때문에 다른 방식을 접근해야함...
 * 너무 어려운데 고쳐야할 필요성을 느끼고 일단 stop..
 *
 *
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     *
     * 이 부분을 아예 다시 하자....
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication){
        CustomUser user = ((CustomUser) authentication.getPrincipal());

        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
//                .setSubject(""+ user.get.getId())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", roles)
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);

    }
}
