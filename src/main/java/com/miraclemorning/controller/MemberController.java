package com.miraclemorning.controller;


import com.miraclemorning.domain.Member;
import com.miraclemorning.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log
@RestController
@NoArgsConstructor
@RequestMapping("/users")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Member> register(@RequestBody Member member) {

        memberService.register(member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ResponseEntity<Map> checkValidEmail(@RequestParam(name = "email") String email) {

        Map resMap = new HashMap<>();

        Boolean existsByEmail = memberService.existsByEmail(email);

        if (existsByEmail) {
            resMap.put("isExist", true);
            resMap.put("message", "이미 존재하는 유저 이메일입니다.");
        } else {
            resMap.put("isExist", false);
            resMap.put("message", "회원가입이 가능한 이메일입니다.");
        }
        return new ResponseEntity<Map>(resMap, HttpStatus.OK);
    }
}
