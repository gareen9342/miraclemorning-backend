package com.miraclemorning.service;

import com.miraclemorning.domain.Member;
import com.miraclemorning.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    // ======== member의 id값으로 조회하기 =======//
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    // ======= member의 email로 조회하기 =======//
    public Member findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    //========= member의 email 로 멤버 있는 지, 없는 지 =========//
    public int countByEmail(String email){
        return memberRepository.countByEmail(email);
    }
}
