package com.miraclemorning.controller;

import com.miraclemorning.common.security.domain.CustomUser;
import com.miraclemorning.domain.Member;
import com.miraclemorning.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log
@RestController
@RequestMapping("/users")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberController() {
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Member> register(@RequestBody Member member){
        String password = member.getPassword();
        member.setPassword(passwordEncoder.encode(password));
        memberService.register(member);

        member.setPassword("");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @RequestMapping(value="/validate", method=RequestMethod.GET)
    public ResponseEntity<Map> checkValidEmail(@RequestParam(name="email") String email){

        Map resMap = new HashMap<>();

        Long existUserCount = memberService.countByEmail(email);

        if(existUserCount > 0){
            resMap.put("isExist", true);
            resMap.put("message","이미 존재하는 유저 이메일입니다.");
        }else{
            resMap.put("isExist", false);
            resMap.put("message", "회원가입이 가능한 이메일입니다.");
        }
        return new ResponseEntity<Map>(resMap, HttpStatus.OK);
    }

    @RequestMapping(value="")
    public ResponseEntity<Member> getMyinfo(@AuthenticationPrincipal CustomUser customUser) throws Exception{
        Long userId = customUser.getMemberId();
        log.info("user id = " + userId);
        Member member = memberService.findOne(userId);
        member.setPassword("");
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}
