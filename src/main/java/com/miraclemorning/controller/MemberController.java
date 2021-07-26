package com.miraclemorning.controller;

import com.miraclemorning.domain.Member;
import com.miraclemorning.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/users")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Member> register(@RequestBody Member member){
        String password = member.getPassword();
        member.setPassword(passwordEncoder.encode(password));
        memberService.register(member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }


}
