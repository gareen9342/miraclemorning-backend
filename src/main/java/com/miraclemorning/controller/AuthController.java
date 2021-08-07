package com.miraclemorning.controller;

import com.miraclemorning.common.security.TokenProvider;
import com.miraclemorning.domain.AuthProvider;
import com.miraclemorning.domain.Member;
import com.miraclemorning.exception.BadRequestException;
import com.miraclemorning.payload.ApiResponse;
import com.miraclemorning.payload.AuthResponse;
import com.miraclemorning.payload.LoginRequest;
import com.miraclemorning.payload.SignupRequest;
import com.miraclemorning.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


// 이곳은 다른 곳에 인증을 맡기지 않고 서버 내에서 로컬로 유저를 등록하고 처리하는 부분이다.
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login") // login request를 수행. authenticationManager가 해준다..
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }


    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if(memberRepository.existsByEmail(signupRequest.getEmail())){
            throw new BadRequestException("Email address already in use");
        }
        // creating user's account
        Member member = new Member();

        member.setName(signupRequest.getName());
        member.setEmail(signupRequest.getEmail());
        member.setPassword(signupRequest.getPassword());
        member.setProvider(AuthProvider.local);

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        Member result = memberRepository.save(member);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
