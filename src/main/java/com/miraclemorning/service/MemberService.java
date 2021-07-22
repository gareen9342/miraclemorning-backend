package com.miraclemorning.service;

import com.miraclemorning.domain.Member;
import com.miraclemorning.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
