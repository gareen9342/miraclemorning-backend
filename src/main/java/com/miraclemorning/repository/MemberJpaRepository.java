package com.miraclemorning.repository;

import com.miraclemorning.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

//    @Query("SELECT COUNT(m.id) > 0 " +
//            "FROM MEMBER m " +
//            "WHERE m.member_email =:email")
//    boolean exists(@Param("email") String email);

    Long countByEmail(String email);

    Member findByEmail(String useremail);

//    @Query("SELECT * FROM MEMBER m WHERE m.member_email =:email")
//    List<Member> findByEmail(String email);
}
