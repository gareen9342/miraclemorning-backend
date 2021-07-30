package com.miraclemorning.controller;

import com.miraclemorning.domain.Invitation;
import com.miraclemorning.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/team")
@PreAuthorize("isAuthenticated()")
public class TeamController {

    @PostMapping
    public ResponseEntity<Team> register(@Validated @RequestBody Team team) throws Exception{
        return new ResponseEntity<Team>(team, HttpStatus.CREATED);
    }

    // TODO : 회원관리 기능은 관리자만 할 수 있도록 구현하기
    // TODO : 회원 초대 기능에 대한 권한은 어떻게 해야할 지 모르겠당..ㅠ
    @PostMapping(value="")
    public ResponseEntity<String> invite(@RequestBody Invitation invitation){
        
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
