//package com.miraclemorning.repository;
//
//import com.miraclemorning.domain.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class MemberRepository {
//    private final EntityManager em;
//
//    // ========= save member =========//
//    public void save(Member member){
//        em.persist(member);
//    }
//
//    // ========= find by id =========//
//    public Member findOne( Long id ){
//        return em.find(Member.class, id);
//    }
//
//    // ========= find by email
//    //
//    //
//    // =========//
//    public Member findByEmail(String email){
//        return em.createQuery("select m from Member m where m.member_email = :email", Member.class)
//                .getSingleResult();
//    }
//
////    // ========= count by email (중복 검사) =========//
//    public boolean existsByEmail(String email){
//        List<Member> list = em.createQuery("select * from member m where m.member_email = :email", Member.class)
//                .setParameter("email", email).getResultList();
//        return list.size() > 0;
//    }
//
//}
