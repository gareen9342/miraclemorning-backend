package com.miraclemorning.repository;

import com.miraclemorning.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Member findOne( Long id ){
        return em.find(Member.class, id);
    }
}
