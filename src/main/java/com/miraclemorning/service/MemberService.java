package com.miraclemorning.service;

import com.miraclemorning.domain.Member;
import com.miraclemorning.domain.MemberAuth;
import com.miraclemorning.repository.MemberJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberJpaRepository memberRepository;

    // ======== 회원가입 ========//
    public void register(Member member) {

        Member memberEntity = new Member();

        memberEntity.setEmail(member.getEmail());
        memberEntity.setPassword(member.getPassword());
        memberEntity.setName(member.getName());
        memberEntity.setAvatar("");

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_MEMBER");

        memberEntity.addAuth(memberAuth);

        memberRepository.save(member);

//        member.setId(memberEntity.getId()); ->이런 짓은 하면 안된다
    }

    // ======== member의 id값으로 조회하기 =======//
    public Member findOne(Long id){
        return memberRepository.findById(id).get();
    }


    //========= member의 email 로 멤버 있는 지, 없는 지 =========//
    public Long countByEmail(String email){
        return memberRepository.countByEmail(email);
    }
}
