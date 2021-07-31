package com.miraclemorning.controller;

import com.miraclemorning.domain.Invitation;
import com.miraclemorning.domain.Member;
import com.miraclemorning.domain.Team;
import com.miraclemorning.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/team")

public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Team> register(@Validated @RequestBody Team team) throws Exception{
        Team savedTeam = teamService.register(team);
        return new ResponseEntity<Team>(savedTeam, HttpStatus.CREATED);
    }

    // TODO : 회원관리 기능은 관리자만 할 수 있도록 구현하기
    // TODO : 회원 초대 기능에 대한 권한은 어떻게 해야할 지 모르겠당..ㅠ
    // TODO : 에러처리, 어떤 에러가 발생할 지 모르겠다
    //======= email로 임시의 url 전송하
    @PostMapping(value="/invitation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> invite(@RequestBody Invitation invitation, @RequestHeader("Host") String host){
        teamService.sendInvitation(invitation, host);
        return new ResponseEntity<>("메일이 보내짐", HttpStatus.OK);
    }

    //====== token 검사
    @GetMapping("/invitation/{token}")
    public ResponseEntity<Map> tokenValidation(@PathVariable("token") String token){
        Map map = teamService.invitationValidation(token); // valid, message, teamId, email
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // ====== 팀에 가입 시키기 //
    @PostMapping("/member")
    public ResponseEntity<String> registerTeamMember(@RequestBody Member member){
        teamService.registerToTeam(member);
        return new ResponseEntity<>("회원 가입 성공", HttpStatus.CREATED);
    }
    // TODO : 이제 API 명세서 만들어야 겠다
}
