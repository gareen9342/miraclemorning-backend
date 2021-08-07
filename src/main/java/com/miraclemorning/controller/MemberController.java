package com.miraclemorning.controller;


import com.miraclemorning.common.security.CurrentUser;
import com.miraclemorning.common.security.UserPrincipal;
import com.miraclemorning.domain.Member;
import com.miraclemorning.exception.ResourceNotFoundException;
import com.miraclemorning.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@NoArgsConstructor
@RequestMapping("/users")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public Member getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return memberRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
