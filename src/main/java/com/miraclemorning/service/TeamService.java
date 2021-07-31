package com.miraclemorning.service;

import com.miraclemorning.common.util.TokenUtil;
import com.miraclemorning.domain.Invitation;
import com.miraclemorning.domain.Member;
import com.miraclemorning.domain.Team;
import com.miraclemorning.repository.InvitationRepository;
import com.miraclemorning.repository.MemberRepository;
import com.miraclemorning.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private final TeamRepository teamRepository;

    @Autowired
    private final InvitationRepository invitationRepository;

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private TokenUtil tokenUtil;

    // ======= 팀 생성 ======= //
    public Team register(Team team){
        return teamRepository.save(team);
    }

    // ======= 초대 이메일 보내기 ======= //
    public void sendInvitation(Invitation invitation, String host){
        Invitation savedInvitation = invitationRepository.save(invitation);

        String token = tokenUtil.createToken(savedInvitation.getId()+"");
        String link = "http://" + host + "/confirm/" + token;
        emailService.sendMail(invitation.getEmail(), "[미라클 모닝] 미라클 모닝 팀 초대 이메일입니다. ", link );
    }

    // ======= 토큰을 검사해 유효한 지 검사하기 ======= //
    public Map invitationValidation(String token){

        HashMap<String, Object> result = new HashMap<>();

        String invitationId = tokenUtil.getInfoFromToken(token);
        Invitation invitation = invitationRepository.findById((long) Integer.parseInt(invitationId)).get();

        result.put("valid", true);
        result.put("message", "유효한 초대장입니다. 회원가입을 진행");
        result.put("teamId", invitation.getTeamId()); // 프론트로 보내줄 초대장. teamId, email을 보내주자
        result.put("email", invitation.getEmail());

        if(invitation.isUsed()){ // 사용된 초대장이라면
            result.put("valid", false);
            result.put("message", "이미 사용된 초대장입니다.");
        }
        if(!tokenUtil.validateToken(token)){ //유효하지 않은 토큰이라면
            result.put("valid", false);
            result.put("message", "초대장이 더이상 유효하지 않거나 올바른 접근이 아닙니다. 초대장을 다시 발급해주세요.");
        }

        return result;
    }

    // ======= 팀에 가입시키기 ======= //
    public void registerToTeam(Member member){
        memberService.register(member);
    }
}
