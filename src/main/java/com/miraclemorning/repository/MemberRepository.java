package com.miraclemorning.repository;

import com.miraclemorning.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Long countByEmail(String email);

    Optional<Member> findByEmail(String useremail);

    void deleteById(Long memberId);
}
