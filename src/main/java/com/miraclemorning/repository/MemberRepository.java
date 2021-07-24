package com.miraclemorning.repository;

import com.miraclemorning.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    // ========= save member =========//
    public void save(Member member){
        em.persist(member);
    }

    // ========= find by id =========//
    public Member findOne( Long id ){
        return em.find(Member.class, id);
    }

    // ========= find by email =========//
    public Member findByEmail(String email){
        return em.createQuery("select m from Member m where m.member_email = :email", Member.class)
                .getSingleResult();
    }

    // ========= count by email (중복 검사) =========//
    public int countByEmail(String email){
        return em.createQuery("select count(*) from member where member_email = :email")
                .setParameter("email", email)
                .getFirstResult();
    }

}
